package com.epam.autopocreator.navigation;
//заменить браузер
import com.teamdev.jxbrowser.chromium.Browser;
/**
 * Одиночка для браузера
 * @author Maxim_Mytaryov
 *
 */
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
