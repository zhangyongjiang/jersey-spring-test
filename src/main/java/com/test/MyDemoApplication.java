package com.test;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.test.resources.TestResource;

public class MyDemoApplication extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public MyDemoApplication() {
		this.register(RequestContextFilter.class);
		this.register(TestResource.class);
		this.register(JacksonFeature.class);
		// Use this for registering a full set of resources.
		// this.packages("org.foo.rest;org.bar.rest");
	}
}
