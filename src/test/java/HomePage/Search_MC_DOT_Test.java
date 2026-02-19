package HomePage;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import static utilities.Screenshot.tearDown1;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})

public class Search_MC_DOT_Test extends StartupCode {

    static WebDriverWait wait;
    final String HOME_PAGE_URL = Config.get("base.url");

    @BeforeClass
    @Parameters("browser")
    public static void Start(@Optional("") String browser) throws InterruptedException {
        AllureEnvWriter.createEnvFile();
        driver = Cross_Browser_factory.setupBrowser(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        test.info("Started Search_MC_DOT_Test on browser: " + (browser.isEmpty() ? Config.get("browser") : browser));
        logger.info("Started Search_MC_DOT_Test on browser: " + (browser.isEmpty() ? Config.get("browser") : browser));
    }

    @BeforeMethod
    public void initTest(Method method) {
        test = Extentreportmanager.getExtentReports().createTest(method.getName());
    }

    @DataProvider(name = "dotReviewData")
    public Object[][] dotReviewData() {
        return new Object[][]{{"123456", 5, "Great broker, fast payments."}, {"234567", 8, "Good experience, timely payments."}, {"345678", 10, "Professional and reliable broker."}};
    }

    @BeforeMethod
    public void goToHomePage() {

        logger.info("Navigating to Home Page before next data set");

        driver.navigate().to(HOME_PAGE_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@aria-label,'Search by')]")));
    }

    @Test(dataProvider = "dotReviewData")
    public void Search_and_Review_Flow(String dotNumber, int paymentDays, String reviewText) throws Exception {

        logger.info("==== START FLOW FOR DOT: " + dotNumber + " ====");
        test.info("Executing review flow for DOT: " + dotNumber);

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Search by')]")));
        dropdown.click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='menuitem']")));
        boolean found = false;
        for (WebElement option : options) {
            if (option.getText().trim().equals("DOT")) {
                option.click();
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "DOT option not found in dropdown!");

        test.pass("DOT filter selected");

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Enter 6-7 digits')]")));

        searchBox.clear();
        searchBox.sendKeys(dotNumber);

        List<WebElement> results = driver.findElements(By.xpath("//a[@role='option']"));

        if (results.isEmpty()) {

            String msg = "Record NOT FOUND for DOT: " + dotNumber;

            logger.warn(msg);
            test.warning(msg);

            Allure.addAttachment("Search Result", "text/plain", "No record found for DOT: " + dotNumber);

            Assert.fail(msg);
        }
        results.get(0).click();

        logger.info("Record found and selected for DOT: " + dotNumber);
        test.pass("Search result selected for DOT: " + dotNumber);


        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 350);");
        WebElement writeReviewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Write a Review']")));
        writeReviewBtn.click();
        test.pass("Opened review form");

        Random random = new Random();
        int overallRating = random.nextInt(5) + 1;
        int sliderRating = random.nextInt(5) + 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='" + overallRating + " stars']"))).click();
        WebElement daysInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='number']")));
        daysInput.clear();
        daysInput.sendKeys(String.valueOf(paymentDays));
        for (int i = 1; i <= 4; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'][normalize-space()='" + sliderRating + "'])[" + i + "]"))).click();
        }
        WebElement reviewBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[contains(@placeholder,'Share details')]")));
        reviewBox.sendKeys(reviewText);
        Assert.assertFalse(reviewBox.getAttribute("value").isEmpty(), "Review box empty for DOT: " + dotNumber);
        Allure.addAttachment("Review Entered", "text/plain", "DOT: " + dotNumber + "\nDays: " + paymentDays + "\nReview: " + reviewText);
        test.pass("Ratings filled for DOT: " + dotNumber);

        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit Review']")));
        submitBtn.click();

        test.pass("Review submitted for DOT: " + dotNumber);
        logger.info("==== END FLOW FOR DOT: " + dotNumber + " ====");
    }


    @AfterMethod
    public void takescreenshot(ITestResult result) throws IOException {
        tearDown1(result);

    }

    @AfterClass
    public static void QuitBrowser() {
        finalizeReport();
        quitDriver();

    }
}
