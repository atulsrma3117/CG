package Signup_Login.Pages;

import Signup_Login.Pages.*;
import Signup_Login.services.OtpApiService;
import org.openqa.selenium.WebDriver;

public class LoginHelper {

    public static void loginWithEmail(WebDriver driver, String email) {

        LoginPage login = new LoginPage(driver);
        login.clickLogin();
        login.enterEmail(email);
        login.clickSendCode();

        String otp = OtpApiService.fetchOtp(email, "login");

        OtpPage otpPage = new OtpPage(driver);
        otpPage.enterOtp(otp);
        otpPage.clickVerify();
    }
}