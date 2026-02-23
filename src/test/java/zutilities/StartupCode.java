package zutilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class StartupCode {


    public static ExtentTest test;
    protected static WebDriver driver;
    static Reuseable data = new Reuseable();
    public static String email = data.Email();
    protected static Reuseable reuse;
    protected static Logger logger;
    protected static ExtentReports extent;
    protected Faker fakedata;
    protected String firstName = data.FirstName();
    protected String lastName = data.LastName();
    protected String password = data.Password();
    protected String zipCode = data.ZipCode();
    protected String phoneNumber = data.PhoneNumber();
    protected String website = data.Website();
    protected String TwoDigitNumber = String.valueOf(data.TwoDigitNumber());

    public static WebDriver setup() throws InterruptedException {
        reuse = new Reuseable();
        driver = reuse.Reuseable1();

        logger = LogManager.getLogger(StartupCode.class);

      //  extent = Extentreportmanager.getExtentReports();
      //  String testClassName = new Object(){}.getClass().getEnclosingClass().getSimpleName();
      //   test = extent.createTest(testClassName);
       // Logs.info(test, "🚀 TEST STARTED: " + testClassName);

        return driver;
    }

    public static void finalizeReport() {

        extent.flush();
        Logs.info(test, " Extent report Loaded.");

    }

    public static void quitDriver() {
        if (driver != null) {

            driver.quit();
        } else {
            logger.warn("Driver is null. Nothing to quit.");
        }
        Logs.info(test, "Browser Closed");
    }
}
