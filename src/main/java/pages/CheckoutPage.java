package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends MainPage { // class that contains methods and locators to be used on the checkout page
    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Methods to return WebElements instead of defining them directly

    private WebElement getEnterVoucherField() {
        return driver.findElement(By.id("checkoutvoucherform-code"));
    }

    private WebElement getApplyVoucherButton() {
        return driver.findElement(By.id("send-voucher"));
    }

    // Verify you are on the checkout page
    public void validateCheckoutUrl() {
        String expectedUrl = "https://carturesti.ro/checkout";
        String actualUrl = driver.getCurrentUrl();
        
        if (!actualUrl.equals(expectedUrl)) {
            throw new IllegalStateException("Expected URL: " + expectedUrl + " but found: " + actualUrl);
        } 
        
        System.out.println("Successfully on the checkout page.");
    }
    
    // Enter a voucher code and click apply
    public void applyVoucher(String voucher) {
        WebElement enterVoucher = getEnterVoucherField();
        WebElement applyVoucher = getApplyVoucherButton();
        
        enterVoucher.click();
        enterVoucher.sendKeys(voucher.toUpperCase());
        applyVoucher.click();

    }

    // Login in while on the checkout page

    public void loginCheckout(String user, String password){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkoutloginform-email")));
        emailField.sendKeys(user);
        driver.findElement(By.id("checkoutloginform-password")).sendKeys(password);
        driver.findElement(By.cssSelector("#w1 > md-menu-bar > div > div.col-sm-12.p-0 > button")).click();
    }

   

     // fill delivery form
    public void deliveryForm (String prenume, String nume, String telefon, String adresa, String codPostal, int judet, int localitate) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.id("checkoutaddressform-firstname")).sendKeys(prenume);
        driver.findElement(By.id("checkoutaddressform-lastname")).sendKeys(nume);
        driver.findElement(By.id("phone-number-input")).sendKeys(telefon);
        driver.findElement(By.id("address-input")).sendKeys(adresa);
        driver.findElement(By.id("zip-code-input")).sendKeys(codPostal);

      // get judet dropdown
    WebElement dropDown = driver.findElement(By.id("counties-dropdown"));
    List <WebElement> judete = dropDown.findElements(By.tagName("option"));
    judete.get(judet).click();
   
  // Wait for Localitate dropdown to be populated with new options
  WebElement dropDown2 = driver.findElement(By.id("checkoutaddressform-id_city"));
  wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//select[@id='checkoutaddressform-id_city']/option"), 1));

  // Re-locate Localitate dropdown options
  List<WebElement> localitati = dropDown2.findElements(By.tagName("option"));
  localitati.get(localitate).click();

    }

}
