package zutilities;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.*;

import static zutilities.StartupCode.test;

public class CustomDriverListener implements WebDriverListener, ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        // Only create a new Extent test if one wasn't created in @BeforeMethod
        if (StartupCode.test == null) {
            ExtentTest et = Extentreportmanager.getExtentReports().createTest(testName);
            StartupCode.test = et;
            Logs.info(et, "🚀 TEST STARTED: " + testName);
        } else {
            Logs.info(StartupCode.test, "🚀 TEST STARTED: " + testName);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            // take screenshot with test name
            Screenshot.takeScreenshot(StartupCode.driver, result.getMethod().getMethodName());
            Logs.pass(StartupCode.driver, StartupCode.test, "✅ TEST PASSED: " + result.getMethod().getMethodName());
        } catch (Exception e) {
            // ignore screenshot errors
            Logs.info(StartupCode.test, "Screenshot on success failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String error = result.getThrowable() != null ? result.getThrowable().toString() : "No Exception";
        try {
            Screenshot.takeScreenshot(StartupCode.driver, result.getMethod().getMethodName());
            // attach to extent via Logs.fail (Screenshot.tearDown will attach on AfterMethod)
        } catch (Exception e) {
            // ignore
        }
        Logs.fail(StartupCode.driver, StartupCode.test, "❌ TEST FAILED: " + error);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        test.skip("⚠️ TEST SKIPPED: " + methodName);

        if (throwable != null) {
            test.skip("Reason: " + throwable.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        Extentreportmanager.getExtentReports().flush();
        Logs.info(test, "=========== SUITE FINISHED ===========");
    }
}
