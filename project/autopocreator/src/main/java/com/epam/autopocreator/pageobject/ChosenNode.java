package com.epam.autopocreator.pageobject;

import com.teamdev.jxbrowser.chromium.dom.DOMNode;

public class ChosenNode {
	private DOMNode node;
	private String selectorType;
	
	public ChosenNode(DOMNode node) {
		this.node = node;
	}
	
	public String getSelector() {
		//TODO селектор
		selectorType = null;
		return null;
	}
	
	public String getHTMLElementType() {
		//TODO тип - батон, текстедит, линк
		return null;
	}
	
	public String getName() {
		//TODO имя
		return null;
	}
}
