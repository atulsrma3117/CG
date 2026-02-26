package Signup_Login.Pages;

import Signup_Login.services.OtpApiService;
import org.openqa.selenium.WebDriver;
import java.util.UUID;

public class SignupHelper {

    public static String signupNewUser(WebDriver driver) {

        String email = "user" + System.nanoTime() + "_" + UUID.randomUUID().toString().substring(0, 8) + "@yopmail.com";
        LoginPage login = new LoginPage(driver);
        login.clickLogin();
        SignupPage signup = login.clickSignUp();
        signup.enterEmail(email);
        signup.clickCreateAccount();
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

        return email; // return email for reuse
    }
}