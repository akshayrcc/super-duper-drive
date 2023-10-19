package com.akshayram.cloudstorage;

import com.akshayram.cloudstorage.model.Credential;
import com.akshayram.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
  /*Getting all the elements on the home web pages which are necessary for the tests to run*/

  @FindBy(id = "btnLogout")
  private WebElement logoutButton;

  @FindBy(id = "fileUpload")
  private WebElement fileUpload;

  @FindBy(id = "btnAddNewNote")
  private WebElement btnAddNewNote;

  @FindBy(id = "btnAddNewCredential")
  private WebElement btnAddNewCredential;

  @FindBy(id = "note-title")
  private WebElement txtNoteTitle;

  @FindBy(id = "nav-notes-tab")
  private WebElement navNotesTab;

  @FindBy(id = "nav-credentials-tab")
  private WebElement navCredentialsTab;

  @FindBy(id = "note-description")
  private WebElement txtNoteDescription;

  @FindBy(id = "btnSaveChanges")
  private WebElement btnSaveChanges;

  @FindBy(id = "tableNoteTitle")
  private WebElement tableNoteTitle;

  @FindBy(id = "tableNoteDescription")
  private WebElement tableNoteDescription;

  @FindBy(id = "btnEditNote")
  private WebElement btnEditNote;

  @FindBy(id = "btnEditCredential")
  private WebElement btnEditCredential;

  @FindBy(id = "note-description")
  private WebElement txtModifyNoteDescription;

  @FindBy(id = "ancDeleteNote")
  private WebElement ancDeleteNote;

  @FindBy(id = "aDeleteCredential")
  private WebElement aDeleteCredential;

  @FindBy(id = "credential-url")
  private WebElement txtCredentialUrl;

  @FindBy(id = "credential-username")
  private WebElement txtCredentialUsername;

  @FindBy(id = "credential-password")
  private WebElement txtCredentialPassword;

  @FindBy(id = "btnCredentialSaveChanges")
  private WebElement btnCredentialSaveChanges;

  @FindBy(id = "tblCredentialUrl")
  private WebElement tblCredentialUrl;

  @FindBy(id = "tblCredentialUsername")
  private WebElement tblCredentialUsername;

  @FindBy(id = "tblCredentialPassword")
  private WebElement tblCredentialPassword;

  private final JavascriptExecutor js;

  private final WebDriverWait wait;

  /*driver, wait and JavascriptExecutor initialised in the constructor*/
  public HomePage(WebDriver driver) {
    PageFactory.initElements(driver, this);
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, 500);
  }

  /*action methods for the webpage clicks*/
  public void logout() {
    logoutButton.click();
  }

  public void editNote() {
    // btnEditNote.click();
    wait.until(ExpectedConditions.elementToBeClickable(btnEditNote)).click();
  }

  public void editCredential() {
    btnEditCredential.click();
  }

  public void deleteNote() {
    // ancDeleteNote.click();
    js.executeScript("arguments[0].click();", ancDeleteNote);
  }

  public void deleteCredential() {
    js.executeScript("arguments[0].click();", aDeleteCredential);
  }

  public void uploadFile() {
    // js.executeScript("arguments[0].click();", fileUpload);
    fileUpload.click();
  }

  public void addNewNote() {
    js.executeScript("arguments[0].click();", btnAddNewNote);
  }

  public void addNewCredential() {
    js.executeScript("arguments[0].click();", btnAddNewCredential);
    // btnAddNewCredential.click();
  }

  /*action methods to set some fields ont the page*/
  public void setNoteTitle(String noteTitle) {
    js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
  }

  public void setCredentialUrl(String url) {
    js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
  }

  public void setCredentialUsername(String username) {
    js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
  }

  public void setCredentialPassword(String password) {
    js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
  }

  /*setting new note title here*/
  public void modifyNoteTitle(String newNoteTitle) {
    // txtNoteTitle.clear();
    wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).clear();
    // txtNoteTitle.sendKeys(newNoteTitle);
    wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).sendKeys(newNoteTitle);
  }

  /*setting new note descripton here*/
  public void modifyNoteDescription(String newNoteDescription) {
    wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).clear();
    wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription))
        .sendKeys(newNoteDescription);
  }

  public void navToNotesTab() {
    js.executeScript("arguments[0].click();", navNotesTab);
    // navNotesTab.click();
  }

  public void navToCredentialsTab() {
    js.executeScript("arguments[0].click();", navCredentialsTab);
    // navCredentialsTab.click();
  }

  public void setNoteDescription(String noteDescription) {
    js.executeScript("arguments[0].value='" + noteDescription + "';", txtNoteDescription);
  }

  public void saveNoteChanges() {
    js.executeScript("arguments[0].click();", btnSaveChanges);
    // btnSaveChanges.click();
  }

  public void saveCredentialChanges() {
    js.executeScript("arguments[0].click();", btnCredentialSaveChanges);
    // btnCredentialSaveChanges.click();
  }

  /*Check if no Notes are present*/
  public boolean noNotes(WebDriver driver) {
    return !isElementPresent(By.id("tableNoteTitle"), driver)
        && !isElementPresent(By.id("tableNoteDescription"), driver);
  }

  /*Check if all 3 credential exists or not*/
  public boolean noCredentials(WebDriver driver) {
    return !isElementPresent(By.id("tblCredentialUrl"), driver)
        && !isElementPresent(By.id("tblCredentialUsername"), driver)
        && !isElementPresent(By.id("tblCredentialPassword"), driver);
  }

  /*Check if element is present or not*/
  public boolean isElementPresent(By locatorKey, WebDriver driver) {
    try {
      driver.findElement(locatorKey);
      return true;
    } catch (org.openqa.selenium.NoSuchElementException e) {
      return false;
    }
  }

  /*get note with description*/
  public Note getFirstNote() {
    //        String title =
    // wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
    //        String description = tableNoteDescription.getText();

    String title = this.tableNoteTitle.getAttribute("innerHTML");
    String description = this.tableNoteDescription.getAttribute("innerHTML");

    return new Note(title, description);
  }

  /*get uname password credentials*/
  public Credential getFirstCredential() {
    String url = wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText();
    String username = tblCredentialUsername.getText();
    String password = tblCredentialPassword.getText();

    return new Credential(url, username, password);
  }
}
