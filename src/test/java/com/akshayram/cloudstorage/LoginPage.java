package com.akshayram.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  /*Getting all the elements on the login web pages which are necessary for the tests to run*/
  @FindBy(id = "inputUsername")
  private WebElement inputUserName;

  @FindBy(id = "inputPassword")
  private WebElement inputPassword;

  @FindBy(id = "submit-button")
  private WebElement submitButton;

  private final JavascriptExecutor js;

  private final WebDriver webDriver;

  /*driver and JavascriptExecutor initialised in the constructor*/
  public LoginPage(WebDriver driver) {
    this.webDriver = driver;
    PageFactory.initElements(driver, this);
    js = (JavascriptExecutor) driver;
  }

  /*getters and setters*/
  public void setUserName(String userName) {
    js.executeScript("arguments[0].value='" + userName + "';", inputUserName);
  }

  public void setPassword(String password) {
    js.executeScript("arguments[0].value='" + password + "';", inputPassword);
  }

  /*login action method*/
  public void login() {
    js.executeScript("arguments[0].click();", submitButton);
    // submitButton.click();
  }
}
