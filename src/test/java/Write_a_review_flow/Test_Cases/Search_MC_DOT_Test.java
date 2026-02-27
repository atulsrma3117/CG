package Write_a_review_flow.Test_Cases;

import Signup_Login.Pages.SignupHelper;
import base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import Write_a_review_flow.Pages.HomePage;
import Write_a_review_flow.Pages.ReviewPage;
import zutilities.Logs;

import java.util.Random;

public class Search_MC_DOT_Test extends BaseTest {

    HomePage homePage;
    ReviewPage reviewPage;
    private String email;

    @DataProvider(name = "dotReviewData")
    public Object[][] dotReviewData() {
        return new Object[][]{{"3943956", 5, "Great broker, fast payments."}};
    }
    @BeforeMethod
    public void navigateToHomePage() {
            email = SignupHelper.signupNewUser(driver);
            Logs.info("Signup completed with email: " + email);

    }
    @AfterMethod
    public void redirectToHomePage() {
            driver.navigate().to(HOME_PAGE_URL);
            Logs.info("Navigated to Home Page for next test iteration");

    }

    @Test(dataProvider = "dotReviewData")
    public void TC_01_Search_and_Review(String dotNumber, int paymentDays, String reviewText) {
        Logs.info("==== START FLOW FOR DOT: " + dotNumber + " ====");

        homePage = new HomePage(driver);
        reviewPage = new ReviewPage(driver);

        homePage.selectDOTFilter();
        homePage.searchDOT(dotNumber);

        reviewPage.openReviewForm();
        reviewPage.enterMCNumber("123456");
        reviewPage.clickVerify();

        Random random = new Random();
        int overallRating = random.nextInt(5) + 1;
        int sliderRating = random.nextInt(5) + 1;
        reviewPage.fillReview(overallRating, sliderRating, paymentDays, reviewText, dotNumber);
        reviewPage.submitReview();
        Logs.pass(driver, "Review submitted for DOT: " + dotNumber);
        Logs.info("==== END FLOW FOR DOT: " + dotNumber + " ====");
    }
}