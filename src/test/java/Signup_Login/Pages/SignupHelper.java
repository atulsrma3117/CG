package Signup_Login.Pages;

import Signup_Login.Pages.*;
import Signup_Login.services.OtpApiService;
import org.openqa.selenium.WebDriver;

public class SignupHelper {

    public static String signupNewUser(WebDriver driver) {

        // Generate unique email every time
        String email = "user" + System.currentTimeMillis() + "@yopmail.com";

        LoginPage login = new LoginPage(driver);
        login.clickLogin();

        SignupPage signup = login.clickSignUp();
        signup.enterEmail(email);
        signup.clickCreateAccount();

        String otp = OtpApiService.fetchOtp(email, "signup");

        OtpPage otpPage = new OtpPage(driver);
        otpPage.enterOtp(otp);
        otpPage.clickVerify();

        return email; // return email for reuse
    }
}