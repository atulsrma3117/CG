package Write_a_review_flow.Test_Cases;

import Signup_Login.Pages.SignupHelper;
import Signup_Login.Test_Cases.SignupLoginTest;
import base.BaseTest;
import org.testng.annotations.*;
import Write_a_review_flow.Pages.HomePage;
import Write_a_review_flow.Pages.ReviewPage;
import zutilities.Logs;

import java.util.Random;

@Listeners({zutilities.CustomDriverListener.class, zutilities.RetryListener.class, io.qameta.allure.testng.AllureTestNg.class})

public class Search_MC_DOT_Test extends BaseTest {

    HomePage homePage;
    ReviewPage reviewPage;
    private String email;

    @DataProvider(name = "dotReviewData")
    public Object[][] dotReviewData() {
        return new Object[][]{{"123456", 5, "Great broker, fast payments."}, {"234567", 8, "Good experience, timely payments."}, {"345678", 10, "Professional and reliable broker."}};
    }

    @BeforeClass
    public void Emailgenerated() {
        email = SignupHelper.signupNewUser(driver);

        Logs.info(test, "Signup completed with email: " + email);
    }

    @BeforeMethod
    public void navigateToHomePage() {
        driver.navigate().to(HOME_PAGE_URL);
        Logs.info(test, "Navigated to Home Page ");
    }

    @Test(dataProvider = "dotReviewData")
    public void Search_and_Review_Flow(String dotNumber, int paymentDays, String reviewText) {

        Logs.info(test, "==== START FLOW FOR DOT: " + dotNumber + " ====");

        homePage = new HomePage(driver);
        reviewPage = new ReviewPage(driver);

        homePage.selectDOTFilter();
        homePage.searchDOT(dotNumber);

        reviewPage.openReviewForm();

        Random random = new Random();
        int overallRating = random.nextInt(5) + 1;
        int sliderRating = random.nextInt(5) + 1;

        reviewPage.fillReview(overallRating, sliderRating, paymentDays, reviewText, dotNumber);

        reviewPage.submitReview();

        Logs.pass(driver, test, "Review submitted for DOT: " + dotNumber);

        Logs.info(test, "==== END FLOW FOR DOT: " + dotNumber + " ====");
    }
}
