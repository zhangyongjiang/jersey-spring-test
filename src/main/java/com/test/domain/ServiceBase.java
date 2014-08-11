package com.test.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ServiceBase {
	@Autowired
	protected ApplicationContext appContext;
}
