package zutilities;

import org.openqa.selenium.HasCapabilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static zutilities.StartupCode.driver;
import static zutilities.StartupCode.test;

public class AllureEnvWriter {
    public static void createEnvFile() {
        try {
            Properties props = new Properties();
            String browserName = ((HasCapabilities) driver)
                    .getCapabilities()
                    .getBrowserName();

            String browserVersion = ((HasCapabilities) driver)
                    .getCapabilities()
                    .getBrowserVersion();
            props.setProperty("Browser", browserName);
            props.setProperty("Browser.Version", browserVersion);
            props.setProperty("Environment", "Staging");
            props.setProperty("Test URL", Config.get("base.url"));
            props.setProperty("Quality Engineer", "Pankaj");
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("Java.Version", System.getProperty("java.version"));

            File allureResultsDir = new File("allure-results");
            if (!allureResultsDir.exists()) {
                boolean created = allureResultsDir.mkdirs();
                if (!created) {
                    Logs.info(test,"❌ Failed to create allure-results directory.");
                    return;
                }
            }

            File envFile = new File(allureResultsDir, "environment.properties");
            try (FileWriter writer = new FileWriter(envFile)) {
                props.store(writer, "Allure Environment Properties");
                Logs.info(test,"✔️ Allure environment.properties file created at " + envFile.getAbsolutePath());
            }

        } catch (IOException e) {
            Logs.info(test,"❌ Failed to create environment.properties: " + e.getMessage());
        }
    }
}