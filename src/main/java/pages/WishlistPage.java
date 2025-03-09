package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishlistPage extends MainPage {

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    //Go to the wishlist menu from the main page

    public void goToWishlistMenu(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //open account dropdown
        WebElement accountMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("accountDropdown")));
        accountMenu.click();
        //select wishlists
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div[2]/md-menu-bar[2]/md-menu-item[3]/ul/li[3]/a")).click();
    }

    //Create a new wishlist

    public void createWishlist(String name){

        WebElement textField = driver.findElement(By.id("wishlist-name"));
        textField.sendKeys(name);
        driver.findElement(By.xpath("//*[@id=\"w0\"]/div/div[1]/span/button")).click();
    }

    }

