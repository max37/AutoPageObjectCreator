package com.autopocreator.pageobject;
import ru.yandex.qatools.htmlelements.element.*;
public class loginPage {

@FindBy(id = "login")
public TextInput loginTextInput;

@FindBy(id = "password")
public TextInput passwordTextInput;

}