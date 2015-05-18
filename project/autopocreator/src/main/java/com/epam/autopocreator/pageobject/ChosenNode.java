package com.epam.autopocreator.pageobject;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.epam.autopocreator.navigation.SingleBrowser;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.DOMNodeType;
import com.teamdev.jxbrowser.chromium.dom.DOMTextAreaElement;
import com.teamdev.jxbrowser.chromium.dom.internal.Node;

public class ChosenNode {
	private DOMNode node;
	private String selector;
	private String htmlType;
	private SelectorType selectorType = SelectorType.NONE;
	
	public ChosenNode(Node node) {
		this.node = node;
		this.selector = getSelector();
		this.htmlType = getHTMLElementType();
	}
	
	public String getSelectorValue() {
		return selector;
	}
	
	public String getHTMLTypeValue() {
		return htmlType;
	}
	
	public String getSelector() {
		if (node instanceof DOMTextAreaElement)  {
			String selector = ((DOMTextAreaElement) node).getAttribute("id");
			if (!selector.isEmpty()) {
				selectorType = SelectorType.ID;
				return selector;
			}
			selector = ((DOMTextAreaElement) node).getAttribute("name");
			if (!selector.isEmpty()) {
				selectorType = SelectorType.NAME;
				return selector;
			}
			selector = findCssSelector(node);
			if (!selector.isEmpty()) {
				selectorType = SelectorType.CSS;
				return selector;
			}
			selector = findXpathSelector(node);
			if (!selector.isEmpty()) {
				selectorType = SelectorType.XPATH;
				return selector;
			}
		}
		if (node instanceof DOMElement) {
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
			selector = findCssSelector(node);
			if (!selector.isEmpty()) {
				selectorType = SelectorType.CSS;
				return selector;
			}
			selector = findXpathSelector(node);
			if (!selector.isEmpty()) {
				selectorType = SelectorType.XPATH;
				return selector;
			}
		}
		System.out.println("CAN'T FIND SELECTOR");
		selectorType = SelectorType.NONE;
		return null;
		
	}
	
	private String findCssSelector(DOMNode node) {
		Map<String, String> attributes = ((DOMElement) node).getAttributes();
		Document doc = Jsoup.parse(SingleBrowser.getSingleBrowser().getBrowser().getHTML());
		for (String attribute : attributes.keySet()) {
			Elements elementsFound = doc.getElementsByAttributeValue(attribute, attributes.get(attribute));
			if (elementsFound.size() == 1) {
				return elementsFound.get(0).cssSelector();
			}
		}
		return "";
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
		switch (selectorType) {
		case ID: return "id";
		case NAME: return "name";
		case CSS: return "css";
		case XPATH: return "xpath";
		default: return "error";
		}
	}
	
	public String getHTMLElementType() {
		if (node instanceof DOMTextAreaElement) {
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("a")) {
				return "Link";
			}
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("img")) {
				return "Image";
			}
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("input")) {
				if (((DOMTextAreaElement) node).getAttribute("type").equalsIgnoreCase("checkbox")) {
					return "CheckBox";
				}
				if (((DOMTextAreaElement) node).getAttribute("type").equalsIgnoreCase("radio")) {
					return "Radio";
				}
				if (((DOMTextAreaElement) node).getAttribute("type").equalsIgnoreCase("file")) {
					return "FileInput";
				}
				if (((DOMTextAreaElement) node).getAttribute("type").equals("submit") ||
						((DOMTextAreaElement) node).getAttribute("type").equalsIgnoreCase("reset")) {
					return "Button";
				}
				return "TextInput";
			}
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("select")) {
				return "Select";
			}
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("form")) {
				return "Form";
			}
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("table")) {
				return "Table";
			}
			if (((DOMTextAreaElement) node).getNodeName().equalsIgnoreCase("button")) {
				return "Button";
			}
		}
		if (node instanceof DOMElement) {
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
		}
		return "HTMLElement";
	}
	
	public String getNewName() {
		if (getSelectorType().equals("id") || getSelectorType().equals("name")) {
			return getSelectorValue() + getHTMLTypeValue();
		}
		return node.getNodeName() + getHTMLElementType() + Counter.getCounter().incNumber();
	}
	
	public String getLastName() {
		String selector = getSelectorType();
		if (selector.equals("id") || selector.equals("name")) {
			return getSelectorValue() + getHTMLTypeValue();
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
		sb.append(getSelectorValue());
		sb.append("\")\r\npublic ");
		sb.append(getHTMLTypeValue());
		sb.append(" ");
		sb.append(getNewName());
		sb.append(";\r\n\r\n");
		if (!getHTMLTypeValue().equals("HTMLElement")) {
			sb.append("public void ");
			sb.append(getLastName());
			sb.append("Click() {\r\n");
			sb.append(getLastName());
			sb.append(".click();\r\n}\r\n\r\n");
			sb.append("public boolean ");
			sb.append(getLastName());
			sb.append("IsDisplayed() {\r\nreturn ");
			sb.append(getLastName());
			sb.append(".isDisplayed();\r\n}\r\n\r\n");
		}
		if (getHTMLTypeValue().equals("TextInput")) {
			sb.append("public void set");
			sb.append(getLastName());
			sb.append("(String newValue) {\r\n");
			sb.append(getLastName());
			sb.append(".clear();\r\n");
			sb.append(getLastName());
			sb.append(".sendKeys(newValue);\r\n}\r\n\r\n");
		}
		
		return sb.toString();
	}
	
}
