package Signup_Login.Pages;

import Signup_Login.services.OtpApiService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.UUID;

public class SignupHelper {

    public static String signupNewUser(WebDriver driver) throws InterruptedException {

        String email = "user" + System.nanoTime() + "_" + UUID.randomUUID().toString().substring(0, 8) + "@yopmail.com";
        LoginPage login = new LoginPage(driver);
        login.clickLogin();
        SignupPage signup = login.clickSignUp();
        signup.enterEmail(email);
        signup.clickCreateAccount();
        By otpSuccessMsg = By.xpath("//div[normalize-space()='OTP sent successfully.']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpSuccessMsg));
        String otp = OtpApiService.fetchOtp(email, "signup");
        OtpPage otpPage = new OtpPage(driver);
        otpPage.enterOtp(otp);
        otpPage.clickVerify();

        try {
            HomePage home = new HomePage(driver);
            if (home.isLoginSuccessful()) {
                home.clickSkipForNow();
            }
        } catch (Exception ignored) {
        }

        return email;
    }
}