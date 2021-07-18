package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*Credentials storage tests */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests extends CloudStorageApplicationTests {

    /*Fixed strings defined here*/
    public static final String DREAM_URL = "https://dreameleven.com/";
    public static final String DREAM_USERNAME = "dravid";
    public static final String DREAM_PASSWORD = "sportifyme";
    public static final String AMAZON_URL = "https://www.amazon.com/";
    public static final String AKSHAY_USERNAME = "akshayrc";
    public static final String AKSHAY_PASSWORD = "hensthere";
    public static final String GITHUB_URL = "https://github.com/";
    public static final String GITHUB_USERNAME = "newton";
    public static final String GITHUB_PASSWORD = "gravitypulls";

    @Test
    public void testCredentialCreation() {
        /*Signup and Login into home page*/
        HomePage homePage = signUpAndLoginTest();

        /*create a login user credentails and verify them*/
        createAndVerifyCredential(AMAZON_URL, AKSHAY_USERNAME, AKSHAY_PASSWORD, homePage);

        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.logout();
    }

    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) {
        /*create new credentials*/
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();

        /*Get the created credentials back*/
        Credential credential = homePage.getFirstCredential();

        /*Assert the credentials*/
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUserName());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) {
        /*home page actions for new creds*/
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        /*set uname pass etc*/
        setCredentialFields(url, username, password, homePage);
        homePage.saveCredentialChanges();
        /*click Ok on the result page*/
        //ResultPage resultPage = new ResultPage(driver);
        //resultPage.clickOk();
        /*need not to click OK as auto redirect in few seconds*/

        homePage.navToCredentialsTab();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        /*Set fields on the Home Page*/
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }

    @Test
    public void testCredentialModification() {
        /*Signup and Login into home page*/
        HomePage homePage = signUpAndLoginTest();

        /*create and verify the user*/
        createAndVerifyCredential(AMAZON_URL, AKSHAY_USERNAME, AKSHAY_PASSWORD, homePage);

        /*fetch first username and password*/
        Credential originalCredential = homePage.getFirstCredential();
        String firstEncryptedPassword = originalCredential.getPassword();

        /*edit creds on homepage*/
        homePage.editCredential();

        String newUrl = GITHUB_URL;
        String newCredentialUsername = GITHUB_USERNAME;
        String newPassword = GITHUB_PASSWORD;

        setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);

        /*save new credentials*/
        homePage.saveCredentialChanges();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.navToCredentialsTab();

        /*verify with the modified credential details*/
        Credential modifiedCredential = homePage.getFirstCredential();
        Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
        Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUserName());

        String modifiedCredentialPassword = modifiedCredential.getPassword();
        Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
        Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);

        homePage.deleteCredential();
        resultPage.clickOk();

        homePage.logout();
    }

    @Test
    public void testDeletion() {
        /*Signup and Login into home page*/
        HomePage homePage = signUpAndLoginTest();

        /*create and verify the three user*/
        createCredential(AMAZON_URL, AKSHAY_USERNAME, AKSHAY_PASSWORD, homePage);
        createCredential(GITHUB_URL, GITHUB_USERNAME, GITHUB_PASSWORD, homePage);
        createCredential(DREAM_URL, DREAM_USERNAME, DREAM_PASSWORD, homePage);

        /*Check if all three credentials are present*/
        Assertions.assertFalse(homePage.noCredentials(driver));

        /*Delete first credentials*/
        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();

        /*Delete second credentials*/
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.navToCredentialsTab();

        /*Delete third credentials*/
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.navToCredentialsTab();

        /*Check if all three credentials are deleted*/
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }
}
