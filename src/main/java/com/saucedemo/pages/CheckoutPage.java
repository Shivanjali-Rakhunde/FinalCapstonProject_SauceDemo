/*package com.saucedemo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CheckoutPage extends BasePage {
    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
   
    private By checkout = By.id("checkout");
    private By continueBtn = By.id("continue");
    private By finish = By.id("finish");
    private By cancel = By.id("cancel");
    private By cartLink = By.className("shopping_cart_link");
    private By summaryItems = By.cssSelector(".cart_item");
    private By confirmation = By.cssSelector(".complete-header");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void openCart() {
        driver.findElement(cartLink).click();
    }

    public void startCheckout() {
        driver.findElement(checkout).click();
    }

    public void fillDetails(String f, String l, String p) {
        driver.findElement(firstName).sendKeys(f);
        driver.findElement(lastName).sendKeys(l);
        driver.findElement(postalCode).sendKeys(p);
    }

    public void continueCheckout() {
        driver.findElement(continueBtn).click();
    }

    public void finishCheckout() {
        driver.findElement(finish).click();
    }

    public String getConfirmation() {
        return driver.findElement(confirmation).getText();
    }

    public void cancelCheckout() {
        driver.findElement(cancel).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isFinishButtonPresent() {
        return driver.findElements(finish).size() > 0;
    }
     public List<WebElement> getSummaryItems() {
        return driver.findElements(summaryItems);
    }
   
   
}*/




package com.saucedemo.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {
    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");

    private By checkout = By.id("checkout");
    private By continueBtn = By.id("continue");
    private By finish = By.id("finish");
    private By cancel = By.id("cancel");
    private By cartLink = By.className("shopping_cart_link");
    private By summaryItems = By.cssSelector(".cart_item");
    private By confirmation = By.cssSelector(".complete-header");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public void startCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkout)).click();
    }

    public void fillDetails(String f, String l, String p) {
        WebElement firstNameInput = wait.until(ExpectedConditions.elementToBeClickable(firstName));
        firstNameInput.clear();
        firstNameInput.sendKeys(f);

        WebElement lastNameInput = wait.until(ExpectedConditions.elementToBeClickable(lastName));
        lastNameInput.clear();
        lastNameInput.sendKeys(l);

        WebElement postalCodeInput = wait.until(ExpectedConditions.elementToBeClickable(postalCode));
        postalCodeInput.clear();
        postalCodeInput.sendKeys(p);
    }

    public void continueCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    public void finishCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(finish)).click();
    }

    public String getConfirmation() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmation)).getText();
    }

    public void cancelCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(cancel)).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public boolean isFinishButtonPresent() {
        return driver.findElements(finish).size() > 0;
    }

    public List<WebElement> getSummaryItems() {
        return driver.findElements(summaryItems);
    }
}
