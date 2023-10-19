package com.akshayram.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

  @LocalServerPort private int port;

  /*Made the driver protected in order to access in the same in the subsequent classes*/
  protected WebDriver driver;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.driver = new ChromeDriver();
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  @Test
  public void getLoginPage() {
    driver.get("http://localhost:" + this.port + "/login");
    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  protected HomePage signUpAndLoginTest() {

    driver.get("http://localhost:" + this.port + "/signup");

    /*get SignUp Page ready*/
    SignupPage signupPage = new SignupPage(driver);
    signupPage.setFirstName("Albert");
    signupPage.setLastName("Einstein");
    signupPage.setUserName("alby");
    signupPage.setPassword("physicsislove");
    signupPage.signUp();

    driver.get("http://localhost:" + this.port + "/login");

    /*get Login Page ready*/
    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUserName("alby");
    loginPage.setPassword("physicsislove");
    loginPage.login();

    return new HomePage(driver);
  }
}
