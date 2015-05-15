package com.epam.autopocreator.navigation;

import com.teamdev.jxbrowser.chromium.Browser;

public class SingleBrowser {
	private Browser browser;
	public static SingleBrowser INSTANCE;
	
	private SingleBrowser() {
		browser = new Browser();
	}
	
	public static SingleBrowser getSingleBrowser() {
		if (INSTANCE == null) {
			INSTANCE = new SingleBrowser();
		}
		return INSTANCE;
	}
	
	public Browser getBrowser() {
		return browser;
	}
}
