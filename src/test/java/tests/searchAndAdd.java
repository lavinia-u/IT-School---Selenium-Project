package tests;
import java.time.Duration;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.CheckoutPage;
import pages.MainPage;
// Search for something and add it to cart
public class searchAndAdd extends baseTest{
    
    @Before
    public void init(){
        // call the method from the baseTest
           if (driver == null) {
            driver = new ChromeDriver();
        }
        setup();
    }
    @Test
    public void searchAndAddTest1(){
        //create an instance of the page
        MainPage mainPage = new MainPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

       String searchTerm = "Ion Creanga";
       mainPage.searchAndEnter(searchTerm);

      // verify the page changed to search results page
       mainPage.validateResultsUrl();

      // verify first result contains search terms
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#coloana-produse > div.row.products-container-grid.ng-scope > div > div:nth-child(1) > prod-grid-box > div.grid-product-details.layout-align-center-center.layout-column > a > h5")));
      assertTrue("Search result does not match the search term!", firstResult.getText().contains(searchTerm));
      System.out.println("Results match search");

        //add first result to cart
        firstResult.click();
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cartu-add-to-cart-btn-x > a")));
        addToCartButton.click();
        System.out.println("Item was added to cart");

        //open shopping cart
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div[2]/div/a")).click();

        //go to order overview page
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div[2]/div/div/div/a")).click();
        //verify we are on the right page
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.validateCheckoutUrl();
    }
   
@After
public void finish(){
    teardown();
}

}
