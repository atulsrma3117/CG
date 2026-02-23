package base;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import zutilities.Logs;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;

import static zutilities.StartupCode.test;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    // ACTION

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        logStep("Click", locator, () -> element.click());
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logStep("Type → " + text, locator, () -> {
            element.clear();
            element.sendKeys(text);
        });
    }

    protected String getText(By locator) {
        WebElement element = waitUntilVisible(locator);
        final String[] value = new String[1];

        logStep("Get Text", locator, () -> value[0] = element.getText());

        return value[0];
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitUntilVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    //WAITS
    public void clickWhenReady(By locator, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }
    protected WebElement waitUntilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    protected void scroll(int pixels) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0, arguments[0]);", pixels);
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    //LOGGER ENGINE

    private void logStep(String action, By locator, Runnable operation) {

        String pageName = this.getClass().getSimpleName();
        String elementName = getReadableName(locator);

        String message = pageName + " → " + action + " → " + elementName;

        long start = System.currentTimeMillis();

        try {
            operation.run();

            long duration = System.currentTimeMillis() - start;

            Logs.info(test, message + " (" + duration + " ms)");
            Allure.step(message + " (" + duration + " ms)");

        } catch (Exception e) {

            Logs.fail(driver, test, message + " ❌ " + e.getMessage());
            Allure.step(message + " ❌ FAILED");

            throw e;
        }
    }

    //READABLE ELEMENT NAME

    private String getReadableName(By locator) {

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {

            if (field.getType().equals(By.class)) {

                field.setAccessible(true);

                try {

                    By value = (By) field.get(this);

                    if (value.toString().equals(locator.toString())) {
                        return field.getName(); // returns variable name
                    }

                } catch (Exception ignored) {}
            }
        }

        return locator.toString();
    }
}