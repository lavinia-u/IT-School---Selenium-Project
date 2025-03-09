package utils;
import java.time.Duration;

import org.openqa.selenium.WebDriver;

public class Waits {

WebDriver driver;

    public void implicitWait() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }
    
}
