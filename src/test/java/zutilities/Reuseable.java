package zutilities;

import net.datafaker.Faker;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Reuseable {
    public WebDriver driver;
    public Logger logger;
    public Faker faker = new Faker();
    public Random random = new Random();
    String downloadPath = System.getProperty("user.dir") + "/downloads";

    public WebDriver Reuseable1() {
        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) downloadDir.mkdirs();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("safebrowsing.enabled", true);
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("--headless=new");
        options.addArguments("start-maximized");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--window-size=1366,768");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        HashMap allow = new HashMap<>();
        allow.put("credentials_enable_service", false);
        allow.put("profile.password_manager_enabled", false);
        allow.put("profile.default_content_setting_values.notifications", 1);
        allow.put("profile.default_content_setting_values.geolocation", 1);
        options.setExperimentalOption("prefs", allow);
        driver = new ChromeDriver(options);
        //  driver.manage().window().maximize();
        driver.get(Config.get("base.url"));
        return driver;

    }

    public String FirstName() {
        return faker.name().firstName();
    }

    public String LastName() {
        return faker.name().lastName();
    }

    public String Email() {
        return FirstName() + LastName().toLowerCase() + "@yopmail.com";
    }

    public String Password() {
        return FirstName().toLowerCase() + "A51%%";
    }

    public String ZipCode() {
        return faker.address().zipCodeByState("CA");
    }

    public String PhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public String Website() {
        return "www." + FirstName().toLowerCase() + ".com";
    }

    public int TwoDigitNumber() {
        return random.nextInt(90) + 10;
    }
}


