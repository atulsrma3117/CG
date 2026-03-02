package Write_a_review_flow.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class HomePage extends BasePage {

    private By dropdown =
            By.xpath("//button[contains(@aria-label,'Search by')]");

    private By options =
            By.xpath("//div[@role='menuitem']");

    private By searchBox =
            By.xpath("//input[contains(@placeholder,'Enter 6-7 digits')]");

    private By results =
            By.xpath("//a[@role='option']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void selectDOTFilter() {

        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdownElement.click();
        List<WebElement> optionList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(options));


        boolean found = false;

        for (WebElement option : optionList) {
            if (option.getText().trim().equals("DOT")) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("DOT option not found in dropdown");
        }
    }
    public boolean searchDOT(String dotNumber) {

        type(searchBox, dotNumber);

        try {
            List<WebElement> resultList = wait
                    .until(ExpectedConditions
                            .visibilityOfAllElementsLocatedBy(results));

            if (resultList.isEmpty()) {
                return false;
            }

            resultList.get(0).click();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
