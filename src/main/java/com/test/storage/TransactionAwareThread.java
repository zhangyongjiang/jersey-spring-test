package com.test.storage;

import java.util.UUID;

public class TransactionAwareThread extends Thread {
	private static ThreadLocal<String> context = new ThreadLocal<String>();
	
	public TransactionAwareThread(Runnable runnable) {
		super(runnable);
		if(context.get() == null) {
			context.set(UUID.randomUUID().toString());
		}
	}
	
}
