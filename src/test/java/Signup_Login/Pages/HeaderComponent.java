package Signup_Login.Pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponent extends BasePage {

    private By profileMenu = By.xpath("(//button[contains(@class,'inline-flex')])[1]");

    private By logoutBtn = By.xpath("//button[@class='flex gap-2 w-full py-2 px-4 mt-[3px] cursor-pointer group rounded-sm hover:bg-shade-100 group transition-all items-center']");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void logout() {
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(profileMenu));
        new Actions(driver).moveToElement(menu).perform();
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
        logout.click();

    }
}
