package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import zutilities.*;

import java.time.Duration;

public class BaseTest extends StartupCode {

    protected static WebDriverWait wait;
    protected final String HOME_PAGE_URL = Config.get("base.url");
    @BeforeClass
    @Parameters("browser")
    public void start(@Optional("") String browser) throws InterruptedException {
        WebDriver originalDriver = Cross_Browser_factory.setupBrowser(browser);
        CustomDriverListener listener = new CustomDriverListener();
        driver = new EventFiringDecorator(listener).decorate(originalDriver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        AllureEnvWriter.createEnvFile();
        Logs.info(test,"Started Test on browser: " +
                (browser.isEmpty() ? Config.get("browser") : browser));
    }

 /*   @BeforeMethod
    public void initTest(java.lang.reflect.Method method) {

        test = Extentreportmanager
                .getExtentReports()
                .createTest(method.getName());

        driver.navigate().to(HOME_PAGE_URL);
    }*/

 @AfterMethod(alwaysRun = true)
 public void takeScreenshot(ITestResult result) throws Exception {

     if (result.getStatus() == ITestResult.FAILURE) {
         Screenshot.tearDown(result);
     }
 }

    @AfterClass
    public void quitBrowser() {
        finalizeReport();
        quitDriver();
    }
}
