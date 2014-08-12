package com.test.storage;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionAwareDataSource implements DataSource {
	private Map<String, Connection> transConnections;
	private static ThreadLocal<String> ctxIdThreadLocal = new ThreadLocal<String>();
	
	@Autowired private DataSource wrapped;
	
	public TransactionAwareDataSource() {
		transConnections = new HashMap<String, Connection>();
	}
	
	private String getCtxId() {
		String id = ctxIdThreadLocal.get();
		if(id == null) {
			id = UUID.randomUUID().toString();
			ctxIdThreadLocal.set(id);
		}
		return id;
	}
	
	public void beginTransaction() {
		ctxIdThreadLocal.set(UUID.randomUUID().toString());
	}
	
	public void commit() throws SQLException {
		Connection conn = transConnections.get(ctxIdThreadLocal.get());
		if(conn != null) {
			try {
				conn.commit();
			} finally {
				conn.close();
			}
		}
	}
	
	public void rollback() throws SQLException {
		Connection conn = transConnections.get(ctxIdThreadLocal.get());
		if(conn != null) {
			try {
				conn.rollback();
			} finally {
				conn.close();
			}
		}
	}
	
	public Connection getConnection() throws SQLException {
		Thread curr = Thread.currentThread();
		Connection connection = null;
		if(curr instanceof DbThread) {
			DbThread dt = (DbThread) curr;
			connection = getConnection(dt.ctxId);
			System.out.println("DbThread conn " + connection);
		}
		else {
			connection = getConnection(getCtxId());
			System.out.println("Main Thread conn " + connection);
		}
		return connection;
	}
	
	public Connection getConnection(String ctxId) throws SQLException {
		Connection conn = transConnections.get(ctxId);
		if(conn == null) {
			conn = wrapped.getConnection();
			transConnections.put(ctxId, conn);
		}
		
		return conn;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return wrapped.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		wrapped.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		wrapped.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return wrapped.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return wrapped.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return wrapped.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return wrapped.isWrapperFor(iface);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		throw new RuntimeException("unimplemented");
	}

	public static class DbThread extends Thread {
		public String ctxId;
		
		public DbThread(Runnable runnable) {
			super(runnable);
			ctxId = ctxIdThreadLocal.get();
		}
	}
}
