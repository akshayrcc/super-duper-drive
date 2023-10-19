package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

  private final JavascriptExecutor js;

  @FindBy(id = "aResultSuccess_1")
  private WebElement aResultSuccess;

  /*driver and JavascriptExecutor initialised in the constructor*/
  public ResultPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
    js = (JavascriptExecutor) driver;
  }

  public void clickOk() {
    js.executeScript("arguments[0].click();", aResultSuccess);
    // aResultSuccess.click();
  }
}
