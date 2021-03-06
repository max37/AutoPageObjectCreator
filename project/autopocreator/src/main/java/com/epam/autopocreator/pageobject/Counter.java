package com.epam.autopocreator.pageobject;

/**
 * Одиночка для счетчика
 * @author Maxim_Mytaryov
 *
 */
public class Counter {
	private static Counter INSTANCE;
	private int number;
	
	private Counter() {
		number = 0;
	}
	
	public static Counter getCounter() {
		if (INSTANCE == null) {
			INSTANCE = new Counter();
		}
		return INSTANCE;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int incNumber() {
		return ++number;
	}
}
