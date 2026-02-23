package Signup_Login.Test_Cases;

import Signup_Login.Pages.LoginPage;
import Signup_Login.Pages.OtpPage;
import base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import zutilities.Logs;

public class SignupLoginValidationTest extends BaseTest {
    @BeforeClass
    public void generateEmail() {
        email = "user" + System.currentTimeMillis() + "@yopmail.com";
    }
    @Test(priority = 1)
    public void signupValidationFlow() throws InterruptedException {

        LoginPage login = new LoginPage(driver);
        login.clickLogin();
        login.clickSignUp();

        String[] invalidEmails = {"pnkj", "pnkj@", "pnkj@.com", "pnkj.com", "pnkj.com@", email};

        login.validateInvalidEmails(invalidEmails);

        OtpPage otp = new OtpPage(driver);
        otp.enterOtp("222222");
        otp.clickVerify();

        Logs.info(test, "Invalid OTP Toast: " + otp.getInvalidOtpMessage());
        otp.clearOtpField();
        otp.clickResend();
        Logs.info(test, "Resend OTP Toast: " + otp.getResendMessage());
        otp.clickChangeEmail();
    }

    @Test(priority = 2)
    public void loginValidationFlow() {

        LoginPage login = new LoginPage(driver);
        login.clickLogin();

        String[] invalidEmails = {"pnkj", "pnkj@", "pnkj@.com", "pnkj.com", "pnkj.com@", email};

        login.validateInvalidEmails(invalidEmails);

        Logs.info(test, "User Not Found Message: " + login.getUserNotFoundMessage());
    }
}
