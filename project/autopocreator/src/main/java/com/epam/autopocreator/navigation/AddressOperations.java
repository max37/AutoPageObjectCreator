package com.epam.autopocreator.navigation;

public abstract class AddressOperations {
	
	public static String getPageName(String URL) {
		System.out.println(URL.lastIndexOf("/"));
		System.out.println(URL.length());
		if (URL.lastIndexOf("/") >= URL.length() - 1) {
			return "Main";
		}
		String endOfUrl = URL.substring(URL.lastIndexOf("/") + 1, URL.length());
		if (endOfUrl.indexOf("?") > -1) {
			endOfUrl = endOfUrl.substring(0, endOfUrl.indexOf("?"));
		}
		if (endOfUrl.indexOf(".") > -1) {
			endOfUrl = endOfUrl.substring(0, endOfUrl.indexOf("."));
		}
		if (endOfUrl.isEmpty()) {
			return "Main";
		}
		return endOfUrl;
	}
}
