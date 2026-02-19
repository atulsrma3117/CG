package Write_a_review_flow.Pages;

import base.BasePage;
import io.qameta.allure.Allure;
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

        click(dropdown);

        List<WebElement> optionList = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(options));

        boolean found = false;

        for (WebElement option : optionList) {
            if (option.getText().trim().equals("DOT")) {
                option.click();
                found = true;
                break;
            }
        }

        Assert.assertTrue(found, "DOT option not found in dropdown!");
    }

    public void searchDOT(String dotNumber) {

        type(searchBox, dotNumber);

        List<WebElement> resultList = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(results));
        if (resultList.isEmpty()) {

            String msg = "Record NOT FOUND for DOT: " + dotNumber;

            Allure.addAttachment("Search Result",
                    "text/plain",
                    msg);

            Assert.fail(msg);
        }

        resultList.get(0).click();
    }
}
