package Signup_Login.Test_Cases;

import Signup_Login.Pages.*;
import Signup_Login.services.OtpApiService;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignupLoginTest extends BaseTest {
    private String email;

    @BeforeClass
    public void generateEmail() {
        email = "user" + System.currentTimeMillis() + "@yopmail.com";
    }
    @Test(priority = 1)
    public void signupFlow() {

        LoginPage login = new LoginPage(driver);
        login.clickLogin();

        SignupPage signup = login.clickSignUp();
        signup.enterEmail(email);
        signup.clickCreateAccount();

        String otp = OtpApiService.fetchOtp(email, "signup");

        OtpPage otpPage = new OtpPage(driver);
        otpPage.enterOtp(otp);
        otpPage.clickVerify();

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isLoginSuccessful());
    }

    @Test(priority = 2)
    public void logoutFlow() {
        new HeaderComponent(driver).logout();
    }

    @Test(priority = 3)
    public void loginFlow() {

        LoginPage login = new LoginPage(driver);
        login.clickLogin();
        login.enterEmail(email);
        login.clickSendCode();
        String otp = OtpApiService.fetchOtp(email, "login");
        OtpPage otpPage = new OtpPage(driver);
        otpPage.enterOtp(otp);
        otpPage.clickVerify();

        Assert.assertTrue(new HomePage(driver).isLoginSuccessful());
    }
}
