package zutilities;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Logs {
    private static Logger Logger() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        return LogManager.getLogger(className);
    }

    private static void attachScreenshot(WebDriver driver) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );

        } catch (Exception e) {
            Logger().warn("⚠️ Failed to capture screenshot: " + e.getMessage());
        }
    }

    public static void pass(WebDriver driver, String message) {
        Logger().info("✅ " + message);

        Allure.step("✅ " + message, () -> {
            attachScreenshot(driver);
        });
    }
    public static void fail(WebDriver driver, String message) {
        Logger().error("❌ " + message);

        Allure.step("❌ " + message, () -> {
            attachScreenshot(driver);
        });
    }

    public static void info(String message) {
        Logger().info("ℹ️ " + message);
        Allure.step("ℹ️ " + message);
    }

    public static void warn(String message) {
        Logger().warn("⚠️ " + message);
        Allure.step("⚠️ " + message);
    }

    public static void error(String message) {
        Logger().error("🛑 " + message);
        Allure.step("🛑 " + message);
    }

    @FunctionalInterface
    public interface CheckedRunnable {
        void run() throws Exception;
    }

    public static void step(String message, CheckedRunnable action) {
        Logger().info("🔹 " + message);
        Allure.step("🔹 " + message, () -> {
            try {
                action.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void step(String message) {
        Logger().info("🔹 " + message);
        Allure.step("🔹 " + message);
    }

}