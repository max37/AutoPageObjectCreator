package com.epam.autopocreator.pageobject;

import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;

public class ChosenNode {
	private DOMNode node;
	private SelectorType selectorType = SelectorType.NONE;
	
	public ChosenNode(DOMNode node) {
		this.node = node;
	}
	
	public ChosenNode(Object node) {
		this.node = (DOMNode) node;
	}
	
	public String getSelector() {
		String selector = ((DOMElement) node).getAttribute("id");
		if (!selector.isEmpty()) {
			selectorType = SelectorType.ID;
			return selector;
		}
		selector = ((DOMElement) node).getAttribute("name");
		if (!selector.isEmpty()) {
			selectorType = SelectorType.NAME;
			return selector;
		}
		selector = findXpathSelector(node);
		if (!selector.isEmpty()) {
			selectorType = SelectorType.XPATH;
			return selector;
		}
		System.err.println("CAN'T FIND SELECTOR");
		selectorType = SelectorType.NONE;
		return null;
		
	}
	
	public String findXpathSelector(DOMNode node) {
		return findXpathSelector(node, "");
	}
	
	public String findXpathSelector(DOMNode node, String xpath) {
    if (node == null) {
        return "";
    }
    String elementName = "";
    if (node instanceof DOMElement) {
        elementName = ((DOMElement) node).getNodeName();
    }
    DOMNode parent = node.getParent();
    if (parent == null) {
        return xpath;
    }
    return findXpathSelector(parent, "/" + elementName + xpath);
}
	
	public String getSelectorType() {
		getSelector();
		switch (selectorType) {
		case ID: return "id";
		case NAME: return "name";
		case XPATH: return "xpath";
		default: return "error";
		}
	}
	
	public String getHTMLElementType() {
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("a")) {
			return "Link";
		}
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("img")) {
			return "Image";
		}
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("input")) {
			if (((DOMElement) node).getAttribute("type").equalsIgnoreCase("checkbox")) {
				return "CheckBox";
			}
			if (((DOMElement) node).getAttribute("type").equalsIgnoreCase("radio")) {
				return "Radio";
			}
			if (((DOMElement) node).getAttribute("type").equalsIgnoreCase("file")) {
				return "FileInput";
			}
			if (((DOMElement) node).getAttribute("type").equals("submit") ||
					((DOMElement) node).getAttribute("type").equalsIgnoreCase("reset")) {
				return "Button";
			}
			return "TextInput";
		}
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("select")) {
			return "Select";
		}
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("form")) {
			return "Form";
		}
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("table")) {
			return "Table";
		}
		if (((DOMElement) node).getNodeName().equalsIgnoreCase("button")) {
			return "Button";
		}
		return "HTMLElement";
	}
	
	public String getNewName() {
		if (getSelectorType().equals("id") || getSelectorType().equals("name")) {
			return getSelector() + getHTMLElementType();
		}
		return node.getNodeName() + getHTMLElementType() + Counter.getCounter().incNumber();
	}
	
	public String getLastName() {
		if (getSelectorType().equals("id") || getSelectorType().equals("name")) {
			return getSelector() + getHTMLElementType();
		}
		return node.getNodeName() + getHTMLElementType() + Counter.getCounter().getNumber();
	}
	
	public DOMNode getNode() {
		return node;
	}
	
	public String getFullDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("@FindBy(");
		sb.append(getSelectorType());
		sb.append(" = \"");
		sb.append(getSelector());
		sb.append("\")\r\n");
		sb.append("public ");
		sb.append(getHTMLElementType());
		sb.append(" ");
		sb.append(getNewName());
		sb.append(";\r\n\r\n");
		return sb.toString();
	}
	
}
