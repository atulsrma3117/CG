package zutilities;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.*;

import static zutilities.StartupCode.test;

public class CustomDriverListener implements WebDriverListener, ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Logs.info(test, "🚀 TEST STARTED: " + result.getMethod().getMethodName());
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
