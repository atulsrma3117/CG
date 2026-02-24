package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HeaderComponent extends BasePage {

    private By profileMenu = By.xpath("//button[.//*[name()='path' and contains(@d,'M12 12C14.7614')]]");

    private By logoutBtn = By.xpath("//button[normalize-space()='Log out']");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void logout() throws InterruptedException {
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(profileMenu));
        Thread.sleep(1000);
        new Actions(driver).moveToElement(menu).perform();
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
        logout.click();

    }
}
