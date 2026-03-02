package Write_a_review_flow.Test_Cases;

import Signup_Login.Pages.OtpPage;
import Signup_Login.Pages.SignupHelper;
import base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import Write_a_review_flow.Pages.HomePage;
import Write_a_review_flow.Pages.ReviewPage;
import org.testng.asserts.SoftAssert;
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
    public void bysignup_navigateToHomePage() throws InterruptedException {
        Logs.step(email = SignupHelper.signupNewUser(driver));
        Logs.info("Signup completed with email: " + email);
    }

    @Test(priority = 1, dataProvider = "dotReviewData")
    public void TC_01_Search_and_Review_without_verification(String dotNumber, int paymentDays, String reviewText) throws InterruptedException {
        Logs.info("==== Searched DOT number: " + dotNumber + " ====");
        homePage = new HomePage(driver);
        reviewPage = new ReviewPage(driver);

        homePage.selectDOTFilter();
        homePage.searchDOT(dotNumber);
        reviewPage.openReviewForm();

        SoftAssert softAssert = new SoftAssert();

        boolean popupDisplayed = reviewPage.isnumpopupContainerDisplayed();
        Logs.info("Business Verification popup displayed: " + popupDisplayed);
        softAssert.assertTrue(popupDisplayed, "Business Verification popup should be displayed");

        String actualTitle = reviewPage.getnumPopuptitle();
        String actualMessage = reviewPage.getnumpopupMessage();
        Logs.info("Popup Title: " + actualTitle);
        Logs.info("Popup Message: " + actualMessage);

        boolean titleValid = actualTitle.contains("Please verify your business profile");
        Logs.info("Popup title contains expected text: " + titleValid);
        softAssert.assertTrue(titleValid, "Popup title should contain expected text");

        boolean messageValid = actualMessage.contains("Help us confirm your account type");
        Logs.info("Popup message contains expected text: " + messageValid);
        softAssert.assertTrue(messageValid, "Popup message should contain expected text");

        boolean mcDropdownVisible = reviewPage.isnummcDropdownDisplayed();
        Logs.info("MC Dropdown displayed: " + mcDropdownVisible);
        softAssert.assertTrue(mcDropdownVisible, "MC Dropdown should be displayed");

        boolean mcInputVisible = reviewPage.isnummcInputDisplayed();
        Logs.info("MC Input displayed: " + mcInputVisible);
        softAssert.assertTrue(mcInputVisible, "MC Input should be displayed");

        boolean skipBtnVisible = reviewPage.isnumskipForNowBtnDisplayed();
        Logs.info("Skip for Now button displayed: " + skipBtnVisible);
        softAssert.assertTrue(skipBtnVisible, "Skip for Now button should be displayed");

        boolean verifyBtnVisible = reviewPage.isnumverifyAndContinueBtnDisplayed();
        Logs.info("Verify & Continue button displayed: " + verifyBtnVisible);
        softAssert.assertTrue(verifyBtnVisible, "Verify & Continue button should be displayed");

        boolean verifyBtnDisabled = reviewPage.isVerifyAndContinueDisabled();
        Logs.info("'Verify & Continue' button disabled: " + verifyBtnDisabled);
        softAssert.assertTrue(verifyBtnDisabled, "'Verify & Continue' button should be disabled before entering MC number");
        softAssert.assertAll();

    }

/*
    @Test(priority = 1,dataProvider = "dotReviewData")
    public void TC_02_Search_and_Review_with_verififcation(String dotNumber, int paymentDays, String reviewText) {
        Logs.info("==== START FLOW FOR DOT: " + dotNumber + " ====");

        homePage = new HomePage(driver);
        reviewPage = new ReviewPage(driver);

        homePage.selectDOTFilter();
        homePage.searchDOT(dotNumber);

        reviewPage.openReviewForm();
        Random random = new Random();
        int randomThreeDigits = 100 + random.nextInt(900);
        String mcNumber = "123" + randomThreeDigits;
        reviewPage.enterMCNumber(mcNumber);reviewPage.clickVerify();
        int overallRating = random.nextInt(5) + 1;
        int sliderRating = random.nextInt(5) + 1;
        reviewPage.fillReview(overallRating, sliderRating, paymentDays, reviewText, dotNumber);
        reviewPage.submitReview();
        Logs.pass(driver, "Review submitted for DOT: " + dotNumber);
        Logs.info("==== END FLOW FOR DOT: " + dotNumber + " ====");
    }*/
}