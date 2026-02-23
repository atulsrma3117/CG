package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import zutilities.Logs;

import static zutilities.StartupCode.test;

public class LoginPage extends BasePage {

    private By loginBtn = By.xpath("//button[normalize-space()='Login']");
    private By signUpBtn = By.xpath("//button[normalize-space()='Sign Up']");
    private By emailInput = By.xpath("//input[@placeholder='you@carriercompany.com']");
    private By sendCodeBtn = By.xpath("//button[normalize-space()='Send Code']");
    private By userNotFoundAlert = By.xpath("//div[normalize-space()='User not found.']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickLogin() {
        click(loginBtn);
    }

    public SignupPage clickSignUp() {
        click(signUpBtn);
        return new SignupPage(driver);
    }

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void clickSendCode() {
        click(sendCodeBtn);
    }

    public void validateInvalidEmails(String[] emails) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String email : emails) {

            driver.findElement(emailInput).clear();
            driver.findElement(emailInput).sendKeys(email);
            driver.findElement(emailInput).submit();

            String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", driver.findElement(emailInput));

            if (validationMessage.isEmpty()) {
                Logs.info(test, "Email '" + email + "' is accepted no validation message (Possibly valid email)");
            } else {
                Logs.info(test, "Email validation for '" + email + "' is  → " + validationMessage);            }
        }
    }

    public String getUserNotFoundMessage() {
        return wait.until(d -> d.findElement(userNotFoundAlert)).getText();
    }
}
