package Write_a_review_flow.Test_Cases;

import Write_a_review_flow.Pages.HomePage;
import Write_a_review_flow.Pages.ReviewPage;
import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import zutilities.Logs;

@Listeners({zutilities.CustomDriverListener.class, zutilities.RetryListener.class, io.qameta.allure.testng.AllureTestNg.class})
@Epic("Review Module")
@Feature("Write Review Without Login")
public class Without_Login_Write_a_Review_Flow_Test extends BaseTest {

    HomePage homePage;
    ReviewPage reviewPage;
    String dotNumber = "1234567";

    @Test(priority = 1)
    @Description("Select DOT filter and search DOT number")
    @Severity(SeverityLevel.NORMAL)
    public void TC_01_Search_and_Review_Flow() {
        Logs.info("==== START FLOW FOR DOT: " + dotNumber + " ====");
        homePage = new HomePage(driver);
        reviewPage = new ReviewPage(driver);
       // homePage.selectDOTFilter();
        homePage.searchDOT(dotNumber);
        Logs.pass(driver, "MC number searched: " + dotNumber);

    }

    @Test(priority = 2, dependsOnMethods = "TC_01_Search_and_Review_Flow")
    @Description("Click Write a Review and validate redirect to login/signup page")
    @Severity(SeverityLevel.BLOCKER)
    public void TC_02_Click_on_Write_a_Review() {
        reviewPage.openReviewForm();
        Logs.info("POP-UP Titile: " + reviewPage.getPopupTitle());
        Assert.assertTrue(reviewPage.getPopupTitle().contains("Sign in to write a review"), "Popup title mismatch!");
        Logs.info("POP-UP Message: " + reviewPage.getPopupMessage());
        Assert.assertTrue(reviewPage.getPopupMessage().contains("sign in or create an account"), "Popup message mismatch!");
        Logs.info("isCancelButtonDisplayed: " + reviewPage.isCancelButtonDisplayed());
        Assert.assertTrue(reviewPage.isCancelButtonDisplayed(), "Cancel button is not displayed!");
        Logs.info("isContinueButtonDisplayed: " + reviewPage.isContinueButtonDisplayed());
        Assert.assertTrue(reviewPage.isContinueButtonDisplayed(), "Continue to Login button is not displayed!");
        Logs.pass(driver, "Sign-in popup validated successfully!");
    }
}




