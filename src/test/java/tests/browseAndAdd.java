package tests;
import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class browseAndAdd extends baseTest {
 
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
    public void browseTest() throws InterruptedException {
// Check all product categories and select one to browse
    // click button to display product categories
    driver.findElement(By.id("buton-produse")).click();

    // store all product categories in a list
    List <WebElement> categoryList = driver.findElements(By.className("md-list-item-text"));

         //get size of the list
         System.out.println("There are " + categoryList.size() + " product categories\n");

          // display all product categories and their index
    int categoryIndex = 0;
    for (WebElement category:categoryList){

        System.out.println(categoryIndex + ": " + category.getText());
        categoryIndex++;
      }
      //access one of the categories from the list using its index
      categoryList.get(6).click();

      //^ Do the same for subcategories
    // Wait for the subcategory list to be visible
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
List<WebElement> subcategoryList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='subcategoriiContainer']/li")));

System.out.println("There are " + subcategoryList.size() + " subcategories\n");

// Display all subcategories and their index
int subcategoryIndex = 0;
for (WebElement subcategory : subcategoryList) {
    System.out.println(subcategoryIndex + ": " + subcategory.getText());
    subcategoryIndex++;
}

// Select the desired subcategory (adjust the index if needed)
WebElement selectedSubcategory = subcategoryList.get(1);

// Scroll the parent container (or specific offset) to make sure subcategory is in view
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", selectedSubcategory);

// Wait until the element is clickable
WebDriverWait waitForClickable = new WebDriverWait(driver, Duration.ofSeconds(5));
waitForClickable.until(ExpectedConditions.elementToBeClickable(selectedSubcategory));

// Perform the click using JavaScript as a fallback if normal click doesn't work
try {
    selectedSubcategory.click(); // Try regular click first
} catch (ElementClickInterceptedException e) {
    System.out.println("Click intercepted, using JavaScript to click.");
    js.executeScript("arguments[0].click();", selectedSubcategory); // Click using JavaScript as a fallback
}

     // apply some filters
String price = "100 - 200";
WebElement checkboxPrice = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//md-checkbox[.//span[text()='" + price + "']]")));
checkboxPrice.click();
wait.until(ExpectedConditions.stalenessOf(checkboxPrice));

String type = "Vinyl";
WebElement checkboxType = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//md-checkbox[.//span[text()='" + type + "']]")));
checkboxType.click();

      //change sorting from smart to newest
      
   // Wait for the dropdown button to be visible

WebElement sortButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"select_26\"]")));


// Click the sort dropdown to open it
js.executeScript("arguments[0].click();", sortButton);

// Wait for the sorting options to be visible

WebElement sortSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
    By.xpath("//*[@id=\"select_option_49\"]/div[1]/span")));

// Click the sorting option
js.executeScript("arguments[0].click();", sortSelect);
      
    //add first result to cart
    WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"coloana-produse\"]/div[1]/div/div[1]/prod-grid-box/div[2]/a/h5")));
    js.executeScript("arguments[0].click();", firstResult);
    WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cartu-add-to-cart-btn-x > a")));
    addToCart.click();

    }
    @After
    public void finish(){
        teardown();
    }

}
