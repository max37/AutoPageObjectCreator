package 
import ru.yandex.qatools.htmlelements.element.*
public class MainPage {
 p@FindBy(xpath = "/HTML/BODY/TABLE/TBODY/TR/TD/FORM/TABLE/TBODY/TR/TD/DIV/BUTTON")
public Button BUTTONButton1;
@FindBy(id = "text")
public TextInput textTextInput;

@FindBy(xpath = "/HTML/BODY/TABLE/TBODY/TR/TD/A")
public Link ALink1;

@FindBy(xpath = "/HTML/BODY/DIV/DIV/DIV/DIV")
public HTMLElement DIVHTMLElement2;

@FindBy(xpath = "/HTML/BODY/DIV/DIV/DIV/DIV/FORM/TABLE/TBODY/TR/TD/BUTTON")
public Button BUTTONButton3;

@FindBy(xpath = "/HTML/BODY/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV/DIV")
public HTMLElement DIVHTMLElement4;

@FindBy(xpath = "/HTML/BODY/TABLE/TBODY/TR/TD/FORM/TABLE/TBODY/TR/TD/DIV/BUTTON")
public Button BUTTONButton1;

}