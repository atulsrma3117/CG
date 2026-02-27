package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import zutilities.Logs;

import java.time.Duration;
import java.util.List;


public class OtpPage extends BasePage {

    private By otpField = By.xpath("//input[@placeholder='Enter 6-digit code']");
    private By verifyBtn = By.xpath("//button[normalize-space()='Verify & Continue']");
    private By resendBtn = By.xpath("//button[normalize-space()='Resend Code']");
    private By invalidOtpToast = By.xpath("//div[contains(text(),'Invalid or expired token.')]");
    private By resendToast = By.xpath("//div[contains(text(),'OTP sent successfully.')]");
    private By changeEmailBtn = By.xpath("//button[normalize-space()='Change Email']");

    public OtpPage(WebDriver driver) {
        super(driver);
    }

    public void enterOtp(String otp) {

            type(otpField, otp);
    }

    public void clickVerify() {
        click(verifyBtn);
    }

    public String getInvalidOtpMessage() {
        return wait.until(d -> d.findElement(invalidOtpToast)).getText();
    }

    public void clickResend() {
        clickWhenReady(resendBtn, 30);
    }

    public String getResendMessage() {
        return wait.until(d -> d.findElement(resendToast)).getText();
    }

    public void clickChangeEmail() {
        click(changeEmailBtn);
    }

    public void clearOtpField() {
        try {
            WebElement e = driver.findElement(otpField);
            e.clear();
            return;
        } catch (Exception ignored) {
        }

        List<WebElement> digits = driver.findElements(By.xpath("//input[@maxlength='1' or contains(@class,'otp')]"));
        for (WebElement d : digits) {
            try {
                d.clear();
            } catch (Exception ignored) {
            }
        }

        // Clear tel input
        try {
            WebElement t = driver.findElement(By.cssSelector("input[type='tel']"));
            t.clear();
        } catch (Exception ignored) {
        }
    }
}
