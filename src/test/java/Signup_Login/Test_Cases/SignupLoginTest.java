package Signup_Login.Test_Cases;

import base.BaseTest;
import Signup_Login.Pages.SignupHelper;
import Signup_Login.Pages.LoginHelper;
import Signup_Login.Pages.HeaderComponent;
import Signup_Login.Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;
import zutilities.Logs;

@Epic("User Authentication Flows")
@Feature("Signup, Login, Logout")
public class SignupLoginTest extends BaseTest {

    private String email;

    @Test(priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a new user can sign up successfully")
    public void TC_01_signupFlow() throws InterruptedException {
        try {
            Logs.info("Starting signup flow");
            Logs.step("Signing up new user", () -> {
                email = SignupHelper.signupNewUser(driver);
            });
            boolean loggedIn = new HomePage(driver).isLoginSuccessful();

            if (loggedIn) {
                Logs.pass(driver, "Signup successful - User logged in");
            } else {
                Logs.info( "Signup failed - User not logged in");
            }

            Assert.assertTrue(loggedIn, "Signup failed - User not logged in");
        } catch (Exception e) {
            Logs.info( "Exception during signup: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    @Story("User logout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the user can logout successfully")
    public void TC_02_logoutFlow() throws InterruptedException {
        try {
            HomePage home = new HomePage(driver);
            Logs.step("Click 'Skip For Now'", home::clickSkipForNow);
            Logs.step("Logging out via Profile menu", () -> new HeaderComponent(driver).logout());

            Logs.pass(driver, "Logout successful");
        } catch (Exception e) {
            Logs.fail(driver, "Exception during logout: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    @Story("User login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a registered user can login successfully using email")
    public void TC_03_loginFlow() {
        try {
            Logs.info("Starting login flow with email: " + email);

            Logs.step("Logging in with email: " + email, () -> LoginHelper.loginWithEmail(driver, email));

            boolean loggedIn = new HomePage(driver).isLoginSuccessful();

            if (loggedIn) {
                Logs.pass(driver, "Login successful");
            } else {
                Logs.fail(driver, "Login failed");
            }

            Assert.assertTrue(loggedIn, "Login failed");
        } catch (Exception e) {
            Logs.fail(driver, "Exception during login: " + e.getMessage());
            throw e;
        }
    }
}