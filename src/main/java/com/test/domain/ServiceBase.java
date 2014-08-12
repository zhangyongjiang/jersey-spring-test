package com.test.domain;

import java.sql.Connection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.test.storage.ShardedJdbcDaoSupport;
import com.test.storage.TransactionAwareDataSource;

public class ServiceBase {
	private ThreadLocal<String> transactionId = new ThreadLocal<String>();
	
	@Autowired
	protected ApplicationContext appContext;
	
	public void startTransaction() {
		transactionId.set(UUID.randomUUID().toString());
	}
	
	protected <T> T getDao(Class<T>cls) {
		try {
			TransactionAwareDataSource dataSource = appContext.getBean(TransactionAwareDataSource.class);
			T dao = cls.newInstance();
			ShardedJdbcDaoSupport sharded = (ShardedJdbcDaoSupport) dao;
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			sharded.setDataSource(new SingleConnectionDataSource(connection, true));
			return dao;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
