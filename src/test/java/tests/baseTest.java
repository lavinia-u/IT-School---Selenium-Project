package tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class baseTest {
    protected WebDriver driver;
    
    public void setup() {
      if (driver != null) {
        driver.manage().window().maximize(); // Maximize the browser window
    } else {
        throw new IllegalStateException("Driver is not initialized. Ensure it's set before calling setup().");
    }
        driver.get("https://carturesti.ro/");
        //click cookies
        WebElement cookie = driver.findElement(By.cssSelector("body > div.cc-window.cc-banner.cc-type-opt-in.cc-theme-block.cc-bottom.cc-color-override-688238583 > div > a.cc-btn.cc-deny"));
        cookie.click();
    }
   
    public void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}