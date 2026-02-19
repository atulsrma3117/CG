package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {

    private By emailInput = By.xpath("//input[@placeholder='you@carriercompany.com']");
    private By createAccountBtn = By.xpath("//button[normalize-space()='Create Account']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void clickCreateAccount() {
        click(createAccountBtn);
    }
}
