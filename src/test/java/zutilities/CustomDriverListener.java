package zutilities;

import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.*;

import static zutilities.StartupCode.driver;


public class CustomDriverListener implements WebDriverListener, ITestListener {

    private static final int maxRetryCount = 2;
    private int retryCount = 0;

    @Override
    public void onTestStart(ITestResult result) {
        Logs.info("🚀 TEST STARTED: " + result.getMethod().getMethodName());
    }

  /*  @Override
    public void onTestFailure(ITestResult result) {
        try {
            if (driver != null) {
                Logs.fail(driver, "❌ TEST FAILED: " + result.getMethod().getMethodName() +
                        (result.getThrowable() != null ? " | Reason: " + result.getThrowable().getMessage() : ""));
            }
        } catch (Exception e) {
           Logs.info("Screenshot capture failed: " + e.getMessage());
        }
    }*/

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        Logs.warn("⚠️ TEST SKIPPED: " + methodName);

        if (throwable != null) {
            Logs.warn("Reason: " + throwable.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        Logs.info("=========== SUITE FINISHED ===========");
    }
    @Override
    public void onStart(ITestContext context) {
        Logs.info("=========== SUITE STARTED ===========");
    }
}
