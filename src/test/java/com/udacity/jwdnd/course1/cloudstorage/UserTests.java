package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

/* User login and actions tests */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    /*Fixed strings defined here*/
    public static final String ALBERT_FIRSTNAME = "Albert";
    public static final String ALBERT_LASTNAME = "Einstein";
    public static final String ALBERT_USERNAME = "TheWierdHairGuy";
    public static final String ALBERT_PASSWORD = "pullMeThere";

    /*get ready the driver for all the tests to run*/
    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    /*clear up the driver post all tests are run*/
    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /*test to check if all 3 pages are accessible*/
    @Test
    public void testPageAccess() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /*test to check if user can signup, login and logout*/
    @Test
    public void testSignUpLoginLogout() {
        /*get to the signup page*/
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        /*fill in new user details*/
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName(ALBERT_FIRSTNAME);
        signupPage.setLastName(ALBERT_LASTNAME);
        signupPage.setUserName(ALBERT_USERNAME);
        signupPage.setPassword(ALBERT_PASSWORD);

        /*click signup action*/
        signupPage.signUp();

        /*get to the login page*/
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        /*fill in newly created user details*/
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName(ALBERT_USERNAME);
        loginPage.setPassword(ALBERT_PASSWORD);

        /*click login action*/
        loginPage.login();

        /*get to the home page*/
        HomePage homePage = new HomePage(driver);

        /*click logout action*/
        homePage.logout();

        /*load the home page back*/
        driver.get("http://localhost:" + this.port + "/home");

        /*the wait is added to load back the home page post logout*/
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Assertions.assertEquals("Login", driver.getTitle());
    }
}
