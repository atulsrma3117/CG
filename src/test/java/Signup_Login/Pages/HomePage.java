package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By successButton = By.cssSelector("button.inline-flex");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginSuccessful() {
        return wait.until(d -> d.findElement(successButton)).isDisplayed();
    }
}
