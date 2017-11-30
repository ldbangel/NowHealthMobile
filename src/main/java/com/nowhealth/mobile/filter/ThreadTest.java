package com.nowhealth.mobile.filter;

public class ThreadTest implements Runnable{
	private int count = 10;
	
	public static void main(String[] args) {
		System.out.println("Thead");
		ThreadTest t  = new ThreadTest();
		for (int i = 0; i < 5; i++) {
			new Thread(t, "Thread"+i).start();
		}
	}

	public void run() {
		count--;
		System.out.println(Thread.currentThread().getName()+" count= "+count);
	}
}
