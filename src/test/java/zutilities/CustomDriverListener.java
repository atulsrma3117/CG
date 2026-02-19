package zutilities;

import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.*;

import static zutilities.StartupCode.test;

public class CustomDriverListener implements WebDriverListener, ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        test = Extentreportmanager.getExtentReports().createTest(testName);

        Logs.info(test, "🚀 TEST STARTED: " + testName);
    }
/*
    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot(StartupCode.driver, StartupCode.test,
                "PASS", "✅ TEST PASSED: " + result.getMethod().getMethodName());
    }
/*
    @Override
    public void onTestFailure(ITestResult result) {
        String error = result.getThrowable() != null ? result.getThrowable().toString() : "No Exception";
        attachScreenshot(StartupCode.driver, StartupCode.test,
                "FAIL", "❌ TEST FAILED: " + error);    }
*/
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

  /*  @Override
    public void beforeGet(WebDriver driver, String url) {
        actionStartTime = System.currentTimeMillis();
        Logs.info(test, "🌐 Navigating to: " + url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logDuration("Navigation");
    }

    @Override
    public void beforeClick(WebElement element) {
        actionStartTime = System.currentTimeMillis();
        Logs.info(test, "🖱 Clicking: " + element.toString());
    }

    @Override
    public void afterClick(WebElement element) {
        logDuration("Click");
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        actionStartTime = System.currentTimeMillis();
        Logs.info(test,
                "⌨ Typing: " + String.join("", keysToSend));
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        logDuration("Typing");
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        Logs.info(test, "🔍 Finding: " + locator.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        Logs.pass(driver, test,
                "✅ Element Found: " + locator.toString());
    }

    @Override
    public void onError(Object target,
                        Method method,
                        Object[] args,
                        InvocationTargetException e) {

        Logs.fail(driver, test,
                "❌ WebDriver Error in: " + method.getName()
                        + "\nCause: " + e.getCause());

        try {
            Screenshot.takeScreenshot(driver, "WebDriver_Error");
        } catch (IOException ex) {
            System.err.println("Screenshot failed.");
        }
    }

    private void logDuration(String action) {
        long duration = System.currentTimeMillis() - actionStartTime;
        Logs.pass(driver, test,
                "✅ " + action + " completed in " + duration + " ms");
    }*/
}
