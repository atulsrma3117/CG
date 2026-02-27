package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import zutilities.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import static zutilities.Screenshot.tearDown;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
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
            Logs.info("🚀 TEST STARTED on browser: " + (browser.isEmpty() ? Config.get("browser") : browser));
    }

 @AfterMethod
  public void takescreenshot(ITestResult result) throws IOException {
       tearDown(result);

   }

    @AfterClass
    public void quitBrowser() {
        quitDriver();
    }

}
