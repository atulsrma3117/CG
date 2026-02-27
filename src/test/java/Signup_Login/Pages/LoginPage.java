package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.*;
import zutilities.Logs;

public class LoginPage extends BasePage {

    private By loginBtn = By.xpath("//button[normalize-space(text())='Login' and contains(@class,'bg-primary-blue')]");
    private By signUpBtn = By.xpath("//button[normalize-space()='Sign Up']");
    private By emailInput = By.xpath("//input[@placeholder='you@carriercompany.com']");
    private By sendCodeBtn = By.xpath("//button[normalize-space()='Send Code']");
    private By userNotFoundAlert = By.xpath("//div[normalize-space()='User not found.']");
    private By logintab = By.xpath("//button[@type='button' and normalize-space()='Login']");



    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickLogin() {
        click(loginBtn);
    }
    public void clickLogintab() {
        click(logintab);
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
            WebElement element = waitUntilVisible(emailInput);
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            element.sendKeys(email);
            element.sendKeys(Keys.ENTER);
            String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", driver.findElement(emailInput));

            if (validationMessage.isEmpty()) {
                Logs.info("Email '" + email + "' is accepted no validation message (Possibly valid email)");
            } else {
                Logs.info("Email validation for '" + email + "' is  → " + validationMessage);            }
        }
    }

    public String getUserNotFoundMessage() {
        return getText(userNotFoundAlert);
    }
}
