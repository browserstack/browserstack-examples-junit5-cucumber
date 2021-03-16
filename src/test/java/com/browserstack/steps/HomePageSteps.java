package com.browserstack.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class HomePageSteps {

    private final StepData stepData;

    public HomePageSteps(StepData stepData) {
        this.stepData = stepData;
    }

    @And("I add two products to cart")
    public void iAddProductsToCart() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(stepData.webDriver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#\\31 > .shelf-item__buy-btn"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#__next > div > div > div.float-cart.float-cart--open > div.float-cart__close-btn"))).click();
        stepData.webDriver.findElement(By.cssSelector("#\\32 > .shelf-item__buy-btn")).click();
    }

    @And("I click on Buy Button")
    public void iClickOnBuyButton() throws InterruptedException {
        stepData.webDriver.findElement(By.cssSelector(".buy-btn")).click();
    }

    @And("I press the Apple Vendor Filter")
    public void iPressTheAppleVendorFilter() throws InterruptedException {
        stepData.webDriver.findElement(By.cssSelector(".filters-available-size:nth-child(2) .checkmark")).click();
    }

    @And("I order by lowest to highest")
    public void iOrderByLowestToHighest() throws InterruptedException {
        WebElement dropdown = stepData.webDriver.findElement(By.cssSelector("select"));
        dropdown.findElement(By.xpath("//option[. = 'Lowest to highest']")).click();
    }

    @Then("I should see user {string} logged in")
    public void iShouldUserLoggedIn(String user) {
        WebDriverWait wait = new WebDriverWait(stepData.webDriver,5);
        try {
            String loggedInUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".username"))).getText();
            Assertions.assertEquals(user, loggedInUser);
        } catch (NoSuchElementException e) {
            throw new AssertionError(user+" is not logged in");
        }
    }

    @Then("I should see no image loaded")
    public void iShouldSeeNoImageLoaded() {
        WebDriverWait wait = new WebDriverWait(stepData.webDriver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        String src = "";
        try {
            src = stepData.webDriver.findElement(By.xpath("//img[@alt='iPhone 12']")).getAttribute("src");
            Assertions.assertEquals("", src);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in logging in");
        }
    }

    @Then("I should see {int} items in the list")
    public void iShouldSeeItemsInTheList(int productCount) {
        WebDriverWait wait = new WebDriverWait(stepData.webDriver,5);
        try{
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner")));
            List<String> values = stepData.webDriver.findElements(By.cssSelector(".shelf-item__title")).stream().map(WebElement::getText).collect(Collectors.toList());
            Assertions.assertEquals(9,values.size());
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

    @Then("I should see prices in ascending order")
    public void iShouldSeePricesInAscendingOrder() {
        try {
            int secondElementPrice = Integer.parseInt(stepData.webDriver.findElement(By.cssSelector("#\\32 5 .val > b")).getText());
            int firstElementPrice = Integer.parseInt(stepData.webDriver.findElement(By.cssSelector("#\\31 9 .val > b")).getText());
            Assertions.assertTrue(secondElementPrice>firstElementPrice);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

}