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
    private int dataSetIndex = 0;

    @DataProvider(name = "dotReviewData")
    public Object[][] dotReviewData() {
        return new Object[][]{{"3943956", 5, "Great broker, fast payments."}, {"234567", 8, "Good experience, timely payments."}, {"4148422", 10, "Professional and reliable broker."}};
    }

    @BeforeMethod
    public void navigateToHomePage() {
        if (dataSetIndex == 0) {
            email = SignupHelper.signupNewUser(driver);
            Logs.info(test, "Signup completed with email: " + email);
        }
    }

    @AfterMethod
    public void redirectToHomePage() {
        if (dataSetIndex < 2) {
            driver.navigate().to(HOME_PAGE_URL);
            Logs.info(test, "Navigated to Home Page for next test iteration");
        }
    }

    @Test(dataProvider = "dotReviewData")
    public void Search_and_Review_Flow(String dotNumber, int paymentDays, String reviewText) {
        Logs.info(test, "==== START FLOW FOR DOT: " + dotNumber + " ====");

        homePage = new HomePage(driver);
        reviewPage = new ReviewPage(driver);

        homePage.selectDOTFilter();
        boolean isFound = homePage.searchDOT(dotNumber);

        if (!isFound) {
            String msg = "DOT NOT FOUND: " + dotNumber;
            Logs.info(test, msg);
            Allure.addAttachment("Search Result", "text/plain", msg);
            dataSetIndex++; // increment for next iteration
            return;
        }

        reviewPage.openReviewForm();
        reviewPage.enterMCNumber("123456");
        reviewPage.clickVerify();

        Random random = new Random();
        int overallRating = random.nextInt(5) + 1;
        int sliderRating = random.nextInt(5) + 1;
        reviewPage.fillReview(overallRating, sliderRating, paymentDays, reviewText, dotNumber);
        reviewPage.submitReview();
        Logs.pass(driver, test, "Review submitted for DOT: " + dotNumber);
        Logs.info(test, "==== END FLOW FOR DOT: " + dotNumber + " ====");

        dataSetIndex++;
    }
}