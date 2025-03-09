package tests;
import java.time.Duration;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.CheckoutPage;
public class checkout extends baseTest{
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
public void checkoutTest() throws InterruptedException {
     //create an instance of the checkoutpage
    CheckoutPage page = new CheckoutPage(driver);
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            
    //add multiple items to cart and go to checkout
        String itemString = "Eminescu";
        String itemString2 = "Creanga";
        String itemString3 = "caiet";
            
         page.searchAndAdd(itemString);
        page.searchAndAdd(itemString2);
        page.searchAndAdd(itemString3);
            
        // Verify you have succesfully switched to checkout page
         page.validateCheckoutUrl();
        System.out.println("Succesfully reached checkout page");
            
            //Try to apply a voucher
            page.applyVoucher("Primavara");
            
            //check if voucher error message apprears after entering an invalid code
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         WebElement errorMessage =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='help-block' and text()='Cod invalid.']")));
         assertEquals("Cod invalid.", errorMessage.getText());
            
        // check if we have free delivery >>>           
        WebElement deliveryTax = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout-products-grid-pjax > div:nth-child(4) > div > strong")));
        if (deliveryTax.isDisplayed() == true) {
            // increase number of items
            WebElement quantityInput = driver.findElement(By.xpath("//input[contains(@class, 'form-control re-validate-cart')]"));
             quantityInput.clear();  // Clear existing value
             quantityInput.sendKeys("5");
            }     
// Wait for any spinner/overlay to disappear
WebDriverWait waitForOverlay = new WebDriverWait(driver, Duration.ofSeconds(10));
waitForOverlay.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner-overlay"))); 

// Click the checkout button
WebElement checkoutButton = driver.findElement(By.xpath("//*[@id='checkout-products-grid-pjax']/div[4]/a"));
checkoutButton.click();
            
//login
page.loginCheckout("disshark@sharklasers.com", "SuperSecret123!");

// fill delivery form
driver.findElement(By.cssSelector("#checkoutaddressform-is_company > label:nth-child(1) > span > input")).click();
page.deliveryForm("Ana", "Popa", "0744345543", "Morara 3", "032334", 6, 7);
WebElement continueToPayment = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#shipping-form > div:nth-child(6) > div:nth-child(2) > button")));
continueToPayment.click();
}
    @After
public void finish(){
    teardown();
}
    
    
}
