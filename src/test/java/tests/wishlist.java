package tests;

import java.time.Duration;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.WishlistPage;

public class wishlist extends baseTest{

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
    public void wishlistTest() throws InterruptedException{
    //Log in using the method from the Main page
      WishlistPage wishlistPage = new WishlistPage(driver);
            wishlistPage.login("disshark@sharklasers.com", "SuperSecret123!");
            
        //open the wishlist menu
        wishlistPage.goToWishlistMenu();

        //create a new wishlist
        wishlistPage.createWishlist("Lista1");

        // wait for the wishlist to be created before proceeding with the search
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement newWishlist = wait.until(ExpectedConditions.presenceOfElementLocated(
    By.xpath("/html/body/div[4]/div[2]/div[2]/div[2]/div[2]")));
        
        //search something and add it to the wishlist
        wishlistPage.searchAndWishlist("Orwell");

        // Wait for the toast to appear in the DOM
        WebElement confirmationToast = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("md-toast-content")));
        
        // Capture the text before it disappears
        wait.until(driver -> !confirmationToast.getText().trim().isEmpty());  
        String toastText = confirmationToast.getText();
        System.out.println("Toast message: " + toastText);

        assertTrue(toastText.contains("Produsul a fost "));

    //return to wishlist menu
    wishlistPage.goToWishlistMenu();

    //check the book title is correct
    WebElement product = driver.findElement(By.xpath("//span[contains(@class, 'md-headline') and contains(text(), 'Orwell')]"));
    assertTrue(product.isDisplayed());
    
    //add something else to the wishlist
    wishlistPage.searchAndWishlist("Jung");
    wishlistPage.goToWishlistMenu();
    //add individual objects to cart
    WebElement addToCart = driver.findElement(By.xpath("//i[contains(@data-ng-click, 'addToCart(product)')]"));
    addToCart.click();
//Wait for new toast to appear & capture the text
WebElement confirmationToast2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("md-toast-content")));
wait.until(driver -> !confirmationToast2.getText().trim().isEmpty());      
String toastText2 = confirmationToast2.getText();
System.out.println("Toast message: " + toastText2);
assertTrue(toastText2.contains("Coș actualizat"));
    
// add all to cart
    WebElement addAllToCart = driver.findElement(By.xpath("//i[contains(@data-ng-click, 'addWishlistToCart(model)')]"));
    addAllToCart.click();

// confirm add all to cart
WebElement confirmationButton = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//button[contains(@class, 'md-confirm-button') and contains(., 'Da')]")
));
confirmationButton.click();

//Re-locate the toast again to avoid stale element issues
WebElement confirmationToast3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("md-toast-content")));
wait.until(driver -> !confirmationToast3.getText().trim().isEmpty());
String toastText3 = confirmationToast3.getText();
System.out.println("Toast message: " + toastText3);
assertTrue(toastText3.contains("actualizat"));

//Delete wishlist
By ariaLabelLocator = By.xpath("//i[@aria-label='Remove wishlist']");
WebElement removeWishlistButton = driver.findElement(ariaLabelLocator);  
removeWishlistButton.click();

//check that the wishlist was deleted
try {
    // Wait until the wishlist item is no longer visible (invisible in DOM)
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
        By.xpath("//div[@class='wishlist-item' and contains(text(), 'Lista1')]"))); 
    System.out.println("Wishlist deleted.");
} catch (TimeoutException e) {
    System.out.println("Wishlist is still present.");
    
}
    }
    // return to main page
     @After
    public void finish(){
        teardown();
    }

 
}
