package Signup_Login.Test_Cases;

import base.BaseTest;
import Signup_Login.Pages.SignupHelper;
import Signup_Login.Pages.LoginHelper;
import Signup_Login.Pages.HeaderComponent;
import Signup_Login.Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignupLoginTest extends BaseTest {

    private String email;

    @Test(priority = 1)
    public void TC_01_signupFlow() {
        email = SignupHelper.signupNewUser(driver);
        Assert.assertTrue(new HomePage(driver).isLoginSuccessful(),
                "Signup failed - User not logged in");
    }

    @Test(priority = 2)
    public void TC_02_logoutFlow() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.clickSkipForNow();
        new HeaderComponent(driver).logout();
}

    @Test(priority = 3, dependsOnMethods = "logoutFlow")
    public void TC_03_loginFlow() {

        LoginHelper.loginWithEmail(driver, email);
        Assert.assertTrue(new HomePage(driver).isLoginSuccessful(),
                "Login failed");
    }
}