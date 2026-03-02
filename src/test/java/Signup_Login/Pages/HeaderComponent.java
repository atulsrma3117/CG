package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HeaderComponent extends BasePage {

    private By profileMenu = By.xpath("/html[1]/body[1]/main[1]/div[1]/nav[1]/div[1]/div[1]/div[1]/div[2]/button[1]");

    private By logoutBtn = By.xpath("//button[normalize-space()='Log out']");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void logout() {

        WebElement menu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(profileMenu));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", menu);

        new Actions(driver)
                .moveToElement(menu)
                .pause(Duration.ofMillis(500))
                .perform();

        WebElement logout = wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutBtn));

        wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
    }
}
