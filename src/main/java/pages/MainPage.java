package pages;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
;

public class MainPage { // class that contains methods and locators to be used on the home page
    WebDriver driver;

    // Constructor
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    
    //Locator
    @SuppressWarnings("FieldMayBeFinal")
    private By search = By.id("search-input");

    //Actions
    public void searchSomething (String something){
       
        driver.findElement(search).sendKeys(something);
    }
    
// Write something in the Search Bar and press Enter
public void searchAndEnter(String searchText) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(search));

    searchBar.sendKeys(searchText);  
    searchBar.sendKeys(Keys.ENTER);  
}
    //Search and add to wishlist

    public void searchAndWishlist(String something){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(search));
        searchAndEnter(something);

         // Wait for search results to load
         WebElement firstResult = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("#coloana-produse div.products-container-grid div:nth-child(1) prod-grid-box a img")));
        firstResult.click();

    // add to wishlist
    WebElement addToWishlist = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/md-card/md-card-content/div[1]")));
    addToWishlist.click();
    WebElement addToWishlistButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("/html/body/div[4]/div[2]/div[3]/div[1]/div[2]/div[2]/div[2]/md-card/md-card-content/div[1]/div/div[2]/strong")));
        addToWishlistButton.click();
}

//Verify we are on the search result page
public void validateResultsUrl() {
    String expectedUrl = "https://carturesti.ro/product/search";
    String actualUrl = driver.getCurrentUrl();
    if (!actualUrl.equals(expectedUrl)) {
        throw new IllegalStateException("Expected URL: " + expectedUrl + " but found: " + actualUrl);
    } 
    System.out.println("Successfully on the results page.");
}


    // Search for an item, add it to cart and go to the checkout page
public void searchAndAdd(String something) {
    // Perform the search first
    searchAndEnter(something);
    // Wait for search results to load
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement firstResult = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#coloana-produse > div.row.products-container-grid.ng-scope > div > div:nth-child(1) > prod-grid-box > a > div > img")
    ));
    // Click on the first search result
    firstResult.click();
    // Add item to cart
    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#cartu-add-to-cart-btn-x > a")
    ));
    addToCartButton.click();
    // Open shopping cart
    WebElement shoppingCart = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("/html/body/div[4]/div[1]/div/div[2]/div/a")
    ));
    shoppingCart.click();

    // Go to order checkout page
    WebElement orderOverview = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("/html/body/div[4]/div[1]/div/div[2]/div/div/div/a")
    ));
    orderOverview.click();


}
    //LOGIN
    public void login(String email, String password){
  //click login button on main page
  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  WebElement loginMenuButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//md-menu-item[3]/button")));
  loginMenuButton.click();
  System.out.println("Login menu clicked.");

  WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginTrigger")));
    loginButton.click();
    System.out.println("Login button clicked.");
    //enter email and password
    WebElement emailField1 = driver.findElement(By.id("loginform-email"));
    WebElement passField1 = driver.findElement(By.id("loginform-password"));
    emailField1.sendKeys(email);
    passField1.sendKeys(password);
    //log in
    driver.findElement(By.xpath("//*[@id=\"modalLoginForm\"]/div[3]/button")).click();

    }

}

