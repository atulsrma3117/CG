package zutilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static zutilities.StartupCode.*;

public class Cross_Browser_factory {
    public static WebDriver setupBrowser(String browser) throws InterruptedException {
        WebDriver driver = null;
        if (browser == null || browser.isEmpty()) {
            browser = Config.get("browser");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = setup();
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                driver.get(Config.get("base.url"));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                driver.get(Config.get("base.url"));
                break;

            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }
        return driver;
    }
}


