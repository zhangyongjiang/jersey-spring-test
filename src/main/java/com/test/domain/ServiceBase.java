package com.test.domain;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.test.storage.ShardedJdbcDaoSupport;

public class ServiceBase {
	private ThreadLocal<String> transactionId = new ThreadLocal<String>();
	
	@Autowired
	protected ApplicationContext appContext;
	
	public void startTransaction() {
		transactionId.set(UUID.randomUUID().toString());
	}
	
	protected <T> T getDao(Class<T>cls) {
		try {
			T dao = cls.newInstance();
			ShardedJdbcDaoSupport sharded = (ShardedJdbcDaoSupport) dao;
			DataSource dataSource = appContext.getBean(DataSource.class);
			sharded.setDataSource(dataSource );
			return dao;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
