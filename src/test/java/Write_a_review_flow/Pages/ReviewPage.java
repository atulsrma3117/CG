package Write_a_review_flow.Pages;

import base.BasePage;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class ReviewPage extends BasePage {

    private By writeReviewBtn = By.xpath("//button[normalize-space()='Write a Review']");

    private By daysInput = By.xpath("//input[@type='number']");

    private By reviewBox = By.xpath("//textarea[contains(@placeholder,'Share details')]");
    private By mc_popup = By.xpath("//input[@placeholder='MC No.']");
    private By verify = By.xpath("//button[normalize-space()='Verify & Continue']");
    private By submitBtn = By.xpath("//button[normalize-space()='Submit Review']");
    private By signInpopup = By.cssSelector("div[role='dialog'][data-state='open']");
    private By popupTitle = By.xpath("//div[@role='dialog']//h2[text()='Sign in to write a review']");
    private By popupMessage = By.xpath("//div[@role='dialog']//p[contains(text(),'sign in or create an account')]");
    private By cancelButton = By.xpath("//div[@role='dialog']//button[normalize-space()='Cancel']");
    private By continueButton = By.xpath("//div[@role='dialog']//button[normalize-space()='Continue to Login']");
    private By numpopupContainer = By.xpath("//div[h2[text()='Please verify your business profile']]");
    private By numpopupTitle = By.xpath("//h2[normalize-space()='Please verify your business profile']");
    private By numpopupMessage = By.xpath("//p[contains(text(),'Help us confirm your account type')]");
    private By nummcDropdown = By.xpath("//button[normalize-space()='MC']");
    private By nummcInput = By.xpath("//input[@placeholder='MC No.']");
    private By numskipForNowBtn = By.xpath("//button[normalize-space()='Skip for Now']");
    private By numverifyAndContinueBtn = By.xpath("//button[normalize-space()='Verify & Continue']");

    public ReviewPage(WebDriver driver) {
        super(driver);
    }

    public void openReviewForm() {
        scroll(350);
        click(writeReviewBtn);
    }

    public void enterMCNumber(String mcNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mc_popup)).clear();
        driver.findElement(mc_popup).sendKeys(mcNumber);
    }

    public void clickVerify() {
        wait.until(ExpectedConditions.elementToBeClickable(verify)).click();

    }

    public void fillReview(int overallRating, int sliderRating, int paymentDays, String reviewText, String dotNumber) {

        click(By.xpath("//button[@aria-label='" + overallRating + " stars']"));

        type(daysInput, String.valueOf(paymentDays));

        for (int i = 1; i <= 4; i++) {
            click(By.xpath("(//button[@type='button'][normalize-space()='" + sliderRating + "'])[" + i + "]"));
        }

        WebElement review = wait.until(ExpectedConditions.visibilityOfElementLocated(reviewBox));

        review.sendKeys(reviewText);

        Assert.assertFalse(review.getAttribute("value").isEmpty(), "Review box empty for DOT: " + dotNumber);

        Allure.addAttachment("Review Entered", "text/plain", "DOT: " + dotNumber + "\nDays: " + paymentDays + "\nReview: " + reviewText);
    }

    public void submitReview() {
        click(submitBtn);
    }

    public void waitForSignInDialog() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInpopup));
    }

    public String getPopupTitle() {
        waitForSignInDialog();
        return driver.findElement(popupTitle).getText();
    }

    public String getPopupMessage() {
        return driver.findElement(popupMessage).getText();
    }

    public boolean isCancelButtonDisplayed() {
        return driver.findElement(cancelButton).isDisplayed();
    }

    public boolean isContinueButtonDisplayed() {
        return driver.findElement(continueButton).isDisplayed();
    }

    public boolean isnumpopupContainerDisplayed() {
        return driver.findElement(numpopupContainer).isDisplayed();
    }

    public String getnumPopuptitle() {
        return driver.findElement(numpopupTitle).getText();
    }

    public String getnumpopupMessage() {
        return driver.findElement(numpopupMessage).getText();
    }

    public boolean isnummcDropdownDisplayed() {
        return driver.findElement(nummcDropdown).isDisplayed();
    }

    public boolean isnummcInputDisplayed() {
        return driver.findElement(nummcInput).isDisplayed();
    }

    public boolean isnumskipForNowBtnDisplayed() {
        return driver.findElement(numskipForNowBtn).isDisplayed();
    }

    public boolean isVerifyAndContinueDisabled() {
        try {
            return !driver.findElement(numverifyAndContinueBtn).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    // Check if Verify & Continue button is displayed
    public boolean isnumverifyAndContinueBtnDisplayed() {
        try {
            return driver.findElement(numverifyAndContinueBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

