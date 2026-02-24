import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class ExcelDataVerifier {

    private WebDriver driver;
    private WebDriverWait wait;

    private int mcColumnIndex = 0;
    private int dotColumnIndex = 1;

    @BeforeClass
    public void setUp() {
        // Setup Chrome in headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // headless mode
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080"); // important for headless mode
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void verifyAllRecords() throws IOException {
        // Read input CSV file and write to output CSV file
        String inputCsvFilePath = "H:\\Automation\\CarrierGuard\\carriers.csv";
        String outputCsvFilePath = "H:\\Automation\\CarrierGuard\\carriers_updated.csv";

        // Setup BufferedReader to read and BufferedWriter to write
        BufferedReader br = new BufferedReader(new FileReader(inputCsvFilePath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputCsvFilePath));

        String line;
        int rowNumber = 1; // To track row numbers

        // Read and write header row (Add status columns)
        String header = br.readLine();
        bw.write(header + ",MC Status,DOT Status\n");

        // Process each row in the CSV
        while ((line = br.readLine()) != null) {
            rowNumber++;
            String[] columns;

            if (line.contains("\t")) {
                columns = line.split("\\t", -1);
            } else {
                columns = line.split(",", -1);
            }
            String mcNumber = columns.length > 0 ? columns[0].trim() : "";
            String dotNumber = columns.length > 1 ? columns[1].trim() : "";

            String mcResult = "";
            if (!mcNumber.isEmpty()) {
                mcResult = searchOnSite(mcNumber, "MC") ? "FOUND" : "NOT FOUND";
            }

            String dotResult = "";
            if (!dotNumber.isEmpty()) {
                dotResult = searchOnSite(dotNumber, "DOT") ? "FOUND" : "NOT FOUND";
            }

            // Write the updated row to the output CSV
            bw.write(String.join(",", columns) + "," + mcResult + "," + dotResult + "\n");

            System.out.println("=== Row " + rowNumber + " ===");
            System.out.println("MC: " + mcNumber + " → " + mcResult);
            System.out.println("DOT: " + dotNumber + " → " + dotResult);
        }

        // Close resources
        br.close();
        bw.close();
    }

    private boolean searchOnSite(String number, String type) {
        try {
            driver.get("http://3.84.224.180/"); // Replace with actual URL

            // Dropdown selection
            By dropdown = By.xpath("//button[contains(@aria-label,'Search by')]");
            wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

            By options = By.xpath("//div[@role='menuitem']");
            List<WebElement> optionList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(options));
            boolean foundOption = false;
            for (WebElement option : optionList) {
                if (option.getText().trim().equals(type)) {
                    option.click();
                    foundOption = true;
                    break;
                }
            }
            if (!foundOption) return false;

            // Enter number in the search field
            By searchBox = By.xpath("//input[contains(@placeholder,'Enter 6-7 digits')]");
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            input.clear();
            input.sendKeys(number);

            // Wait for results
            By results = By.xpath("//a[@role='option']");
            List<WebElement> resultList;
            try {
                resultList = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(results));
            } catch (Exception e) {
                return false;
            }

            return !resultList.isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
        System.out.println("CSV updated with MC/DOT status.");
    }
}