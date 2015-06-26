package com.epam.autopocreator.pageobject;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.epam.autopocreator.navigation.SingleBrowser;
// Заменить на аналоги из Swing
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.DOMNodeType;
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

	/**
	 * Получает селектор созданной ноды
	 * @return
	 */
	public String getSelector() {
		if (node.getNodeType().equals(DOMNodeType.TextNode)) {
			node = node.getParent();
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

	/**
	 * Вычисляет CSS селектор. Для этого используется парсинг к документу Jsoup
	 * @param node
	 * @return
	 */
	private String findCssSelector(DOMNode node) {
		Map<String, String> attributes = ((DOMElement) node).getAttributes();
		Document doc = Jsoup.parse(SingleBrowser.getSingleBrowser()
				.getBrowser().getHTML());
		for (String attribute : attributes.keySet()) {
			Elements elementsFound = doc.getElementsByAttributeValue(attribute,
					attributes.get(attribute));
			if (elementsFound.size() == 1) {
				return elementsFound.get(0).cssSelector();
			}
		}
		return "";
	}

	/**
	 * Получение XPATH селектора. Метод по-умолчанию, для входа
	 * @param node
	 * @return
	 */
	public String findXpathSelector(DOMNode node) {
		return findXpathSelector(node, "");
	}

	/**
	 * Рекурсивный метод получения XPATH селектора
	 * @param node
	 * @param xpath
	 * @return
	 */
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

	/**
	 * Возвращает тип селектора. Селектор хранится в enum типе, но возвращается в строковом виде
	 * @return
	 */
	public String getSelectorType() {
		switch (selectorType) {
		case ID:
			return "id";
		case NAME:
			return "name";
		case CSS:
			return "css";
		case XPATH:
			return "xpath";
		default:
			return "error";
		}
	}

	/**
	 * Возвращает строковое имя соответствующего HTMLElement, в зависимости от типа ноды
	 * @return
	 */
	public String getHTMLElementType() {
		if (node instanceof DOMElement) {
			if (((DOMElement) node).getNodeName().equalsIgnoreCase("a")) {
				return "Link";
			}
			if (((DOMElement) node).getNodeName().equalsIgnoreCase("img")) {
				return "Image";
			}
			if (((DOMElement) node).getNodeName().equalsIgnoreCase("input")) {
				if (((DOMElement) node).getAttribute("type").equalsIgnoreCase(
						"checkbox")) {
					return "CheckBox";
				}
				if (((DOMElement) node).getAttribute("type").equalsIgnoreCase(
						"radio")) {
					return "Radio";
				}
				if (((DOMElement) node).getAttribute("type").equalsIgnoreCase(
						"file")) {
					return "FileInput";
				}
				if (((DOMElement) node).getAttribute("type").equals("submit")
						|| ((DOMElement) node).getAttribute("type")
								.equalsIgnoreCase("reset")) {
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

	/**
	 * Возвращает имя для нового элемента (происходит инкремент номера в конце имени)
	 * @return
	 */
	public String getNewName() {
		if (getSelectorType().equals("id") || getSelectorType().equals("name")) {
			return getSelectorValue() + getHTMLTypeValue();
		}
		return node.getNodeName() + getHTMLElementType()
				+ Counter.getCounter().incNumber();
	}

	/**
	 * Возвращает имя с последним номером (для избежания повторов)
	 * @return
	 */
	public String getLastName() {
		String selector = getSelectorType();
		if (selector.equals("id") || selector.equals("name")) {
			return getSelectorValue() + getHTMLTypeValue();
		}
		return node.getNodeName() + getHTMLElementType()
				+ Counter.getCounter().getNumber();
	}

	public DOMNode getNode() {
		return node;
	}

	/**
	 * Возвращает полное описание элемента в Page Object (вместе с соответствующими методами)
	 * @return
	 */
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
