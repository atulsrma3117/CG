package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    private By successButton = By.cssSelector("button.inline-flex");
    private By skipLocator = By.xpath("//button[normalize-space()='Skip for Now']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginSuccessful() {
        return wait.until(d -> d.findElement(successButton)).isDisplayed();
    }

    public void clickSkipForNow() {
        WebElement skipBtn = wait.until(
                ExpectedConditions.elementToBeClickable(skipLocator)
        );
        skipBtn.click();
    }
}
