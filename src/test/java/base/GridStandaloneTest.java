package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class GridStandaloneTest {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Choose browser here: "chrome" or "firefox"
            String browser = "chrome"; // <-- change to "firefox" if needed

            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.setPlatformName("Windows"); // optional
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                options.setPlatformName("Windows"); // optional
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
            }

            driver.manage().window().maximize();

            // Test steps
            driver.get("https://www.google.com");
            driver.findElement(By.name("q")).sendKeys("Selenium Grid");
            driver.findElement(By.name("q")).submit();

            String title = driver.getTitle();
            System.out.println("Page title is: " + title);

            if (title.contains("Selenium")) {
                System.out.println("Test Passed ✅");
            } else {
                System.out.println("Test Failed ❌");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
