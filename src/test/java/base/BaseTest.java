package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import zutilities.*;

import java.lang.reflect.Method;
import java.time.Duration;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class BaseTest extends StartupCode {
    protected static WebDriverWait wait;
    protected final String HOME_PAGE_URL = Config.get("base.url");

    @BeforeSuite
    public void setupReport() {
        extent = Extentreportmanager.getExtentReports();
    }


    @BeforeClass
    @Parameters("browser")
    public void start(@Optional("") String browser) throws InterruptedException {
        WebDriver originalDriver = Cross_Browser_factory.setupBrowser(browser);
        CustomDriverListener listener = new CustomDriverListener();
        driver = new EventFiringDecorator(listener).decorate(originalDriver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        AllureEnvWriter.createEnvFile();

    }

    @BeforeMethod
    @Parameters("browser")
    public void initTest(Method method, @Optional("") String browser) {
        String className = this.getClass().getSimpleName();
        String methodName = method.getName();

        test = extent.createTest("Test Name - " + className + " - Test Case - " + methodName);

        Logs.info(test, "🚀 TEST STARTED on browser: " +
                (browser.isEmpty() ? Config.get("browser") : browser));

    }

    @AfterClass
    public void quitBrowser() {
        quitDriver();
    }

    @AfterSuite
    public void ReportLoaded() {
        finalizeReport();
    }
}
