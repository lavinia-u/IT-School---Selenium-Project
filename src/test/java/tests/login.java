package tests;

import java.time.Duration;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.MainPage;

public class login  extends baseTest{

    //email:cjxeemvt@sharkalsers.com
    //pass: SuperSecret123!
   
      @Before 
    public void init(){
        // call the method from the baseTest
           if (driver == null) {
            driver = new ChromeDriver();
        }
        setup();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Test
    public void loginTest() {
        
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

      //Try to log in with an incorrect password using the method from the MainPage
      MainPage mainPage = new MainPage(driver);
            mainPage.login("disshark@sharklasers.com", "SuperSecret222!");

    // check that an error message appears
    WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login-form\"]/div[2]/div[2]")));
    assertEquals("Adresă de email sau parolă incorectă.", errorMessage.getText());
    System.out.println("Error message was displayed");

    // login using correct email and pass
    String email = "disshark@sharklasers.com";
    String pass = "SuperSecret123!";

    // Re-locate the email input field after the error appears > email and password input fields have the same ids as before but they are different elements,
    //which is why I used a list to access the correct one
    List<WebElement> emailFields = driver.findElements(By.id("loginform-email"));
    WebElement emailField = emailFields.get(emailFields.size() - 1); // Get the last one
    emailField.clear();
    emailField.sendKeys(email);

  // Re-locate and enter the password
  List<WebElement> passFields = driver.findElements(By.id("loginform-password"));
    WebElement passField = passFields.get(passFields.size() - 1); // Get the last one
    passField.clear();
  passField.sendKeys(pass);
  
  // Click login button
  WebElement loginButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-form\"]/div[6]/button")));
  loginButton2.click();
    

    // Check that we have been succesfully redirected to the main page
    wait.until(ExpectedConditions.urlToBe("https://carturesti.ro/"));
    assertEquals("https://carturesti.ro/", driver.getCurrentUrl());
    System.out.println("Login successful! Redirected to main page.");
    
    }
      @After
    public void finish(){
        teardown();
    }
}
