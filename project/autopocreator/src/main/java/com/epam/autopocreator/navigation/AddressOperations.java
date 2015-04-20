package com.epam.autopocreator.navigation;

public abstract class AddressOperations {
	
	public static String getPageName(String URL) {
		String endOfUrl = URL.substring(URL.lastIndexOf("/"), URL.length() - 1);
		if (endOfUrl.indexOf("?") > -1) {
			endOfUrl = endOfUrl.substring(0, endOfUrl.indexOf("?"));
		}
		if (endOfUrl.indexOf(".") > -1) {
			endOfUrl = endOfUrl.substring(0, endOfUrl.indexOf("."));
		}
		return endOfUrl;
	}
}
