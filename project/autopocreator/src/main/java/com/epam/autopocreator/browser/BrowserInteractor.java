package com.epam.autopocreator.browser;

import javafx.scene.web.*;
public class BrowserInteractor{
	
	WebEngine webEngine;
	private BrowserInteractor() {
		super();
	}
	
	public WebEngine getEngine() {
		if (webEngine == null) {
			webEngine = new WebEngine();
		}
		return webEngine;
	}
	
	public WebEngine getEngine(String url) {
		if (webEngine == null) {
			webEngine = new WebEngine(url);
		}
		return webEngine;
	}
}
