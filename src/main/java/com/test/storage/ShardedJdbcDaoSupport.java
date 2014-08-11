package com.test.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ShardedJdbcDaoSupport extends JdbcDaoSupport {
	@Autowired
	protected ApplicationContext appContext;

}
