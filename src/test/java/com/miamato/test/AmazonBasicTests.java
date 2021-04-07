package com.miamato.test;

import com.miamato.BaseTest;
import com.miamato.LogUtil;
import com.miamato.PropertyManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class AmazonBasicTests extends BaseTest {
    private static final String AMAZON_HOME_PAGE_URL = PropertyManager.getProperty("homepage.url");
    private static final String AMAZON_HOME_PAGE_TITLE = PropertyManager.getProperty("homepage.title");
    private static final String ACCEPT_COOKIES_BUTTON_XPATH = "//input[@id='sp-cc-accept']";
    private static final String SEARCH_FIELD_XPATH = "//input[@id='twotabsearchtextbox']";
    private static final String SEARCH_BUTTON_XPATH = "//input[@id='nav-search-submit-button']";
    private static final String SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH = "//div[@id='departments']//span[@class='a-size-base a-color-base']";
    private static final Integer TARGET_DEPARTMENT_INDEX = 0;

    private static final String SEARCH_TERM_CHILDREN_BOOKS = "children books";
    private static final String SEARCH_TERM_BOOKS_DEPARTMENT = "Books";
    private static final String SEARCH_TERM_BOOKS_AUTHOR = "stephen king books";
    private static final String SEARCH_TERM_BOOKS_GIFT = "book gift";
    private static final String PRODUCT_LINK_TO_CLICK_XPATH = "(//*[@class=\"a-size-mini a-spacing-none a-color-base s-line-clamp-2\"]//a[@class=\"a-link-normal a-text-normal\"])[4]";
    private static final String ADD_TO_BASKET_BUTTON_XPATH = "//input[@id='add-to-cart-button']";
    private static final String BASKET_TOTAL_QUANTITY_XPATH = "//*[@id='nav-cart-count']";
    private static final String TARGET_BASKET_QUANTITY_1 = "1";

    private static final String BASKET_AREA_TO_CLICK_XPATH = "//*[@aria-label='1 item in shopping basket']";
    private static final String DELETE_ITEM_FROM_BASKET_XPATH = "//input[@data-action='delete'][1]";
    private static final String TARGET_BASKET_QUANTITY_0 = "0";

    private static final String CLICK_TO_OPEN_BEST_SELLERS_XPATH = "//div[@id='nav-xshop']//a[contains(text(),'Best Sellers')]";
    private static final String CLICK_TO_OPEN_PRIME_VIDEO_XPATH= "//div[@id='nav-xshop']//a[contains(text(),'Prime Video')]";



    @DataProvider(name = "search-term-set")
    public Object[][] searchTerms() {
        return new Object[][]
                {{SEARCH_TERM_CHILDREN_BOOKS, SEARCH_TERM_BOOKS_DEPARTMENT}
                        ,{SEARCH_TERM_BOOKS_AUTHOR, SEARCH_TERM_BOOKS_DEPARTMENT}
                        ,{SEARCH_TERM_BOOKS_GIFT, SEARCH_TERM_BOOKS_DEPARTMENT}};
    }


    @Test(dataProvider = "search-term-set")
    public void basicAmazonProductSearch(String searchTerm, String expectedDepartmentName){
        navigateToWebPageAndAcceptCookies(AMAZON_HOME_PAGE_URL,AMAZON_HOME_PAGE_TITLE);
        findElementAndFillInputField(SEARCH_FIELD_XPATH,searchTerm);
        driver.findElement(By.xpath(SEARCH_BUTTON_XPATH)).click();
        Assert.assertEquals(driver.findElements(By.xpath(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH)).get(TARGET_DEPARTMENT_INDEX).getText(),expectedDepartmentName);
    }


    @Test
    public void addProductToBasket(){
        navigateToWebPageAndAcceptCookies(AMAZON_HOME_PAGE_URL, AMAZON_HOME_PAGE_TITLE);
        findElementAndFillInputField(SEARCH_FIELD_XPATH,SEARCH_TERM_CHILDREN_BOOKS);
        findElementAndClick(SEARCH_BUTTON_XPATH);
        clickOnElement(PRODUCT_LINK_TO_CLICK_XPATH);
        findElementAndClick(ADD_TO_BASKET_BUTTON_XPATH);
        Assert.assertEquals(TARGET_BASKET_QUANTITY_1, driver.findElement(By.xpath(BASKET_TOTAL_QUANTITY_XPATH)).getText());
    }

    @Test
    public void addProductToBasketAndRemove(){
        navigateToWebPageAndAcceptCookies(AMAZON_HOME_PAGE_URL, AMAZON_HOME_PAGE_TITLE);
        findElementAndFillInputField(SEARCH_FIELD_XPATH,SEARCH_TERM_CHILDREN_BOOKS);
        findElementAndClick(SEARCH_BUTTON_XPATH);
        clickOnElement(PRODUCT_LINK_TO_CLICK_XPATH);
        findElementAndClick(ADD_TO_BASKET_BUTTON_XPATH);
        clickOnElement(BASKET_AREA_TO_CLICK_XPATH);
        findElementAndClick(DELETE_ITEM_FROM_BASKET_XPATH);
        Assert.assertEquals(TARGET_BASKET_QUANTITY_0, driver.findElement(By.xpath(BASKET_TOTAL_QUANTITY_XPATH)).getText());

    }

    @Test
    public void checkMenuItems(){
        navigateToWebPageAndAcceptCookies(AMAZON_HOME_PAGE_URL, AMAZON_HOME_PAGE_TITLE);
        clickOnElement(CLICK_TO_OPEN_BEST_SELLERS_XPATH);
        clickOnElement(CLICK_TO_OPEN_PRIME_VIDEO_XPATH);
    }

    @Step("Accept cookies")
    private static void acceptCookiesIfPopupPresent(){
        try{
            logger.info(AmazonBasicTests.class.getName() + " Accept cookies");

            driver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH)).click();
        } catch(Exception e) {
            logger.info(AmazonBasicTests.class.getName() + " Cookie accept pop-up is not displayed");
            LogUtil.logStackTrace(e, logger);
        }
    }

    @Step("Open Web Page ")
    private static void navigateToWebPageAndAcceptCookies(String url, String title){
        logger.info(AmazonBasicTests.class.getName() + " Navigating to website with url:   " + url);
        driver.navigate().to(url);
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, title);
        acceptCookiesIfPopupPresent();
    }

    @Step("Find input element and put text in")
    private static void findElementAndFillInputField(String xpath, String text){
        logger.info(AmazonBasicTests.class.getName() + " Input: " + text);
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(text);
    }
    @Step("Find input element and click")
    private static void findElementAndClick(String xpath){
        try{
            logger.info(AmazonBasicTests.class.getName() + "Clicking on element with xpath: " + xpath );
            driver.findElement(By.xpath(xpath)).click();
        } catch (Exception e) {
            logger.error(AmazonBasicTests.class.getName() + e.getLocalizedMessage());
            LogUtil.logStackTrace(e, logger);
            throw e;
        }
    }

    @Step("Click on element ")
    private static void clickOnElement(String xpath){
        logger.info(AmazonBasicTests.class.getName() + " Clicking on element with xpath: " + xpath);
        try {
            Actions actions = new Actions(driver);
            WebElement  element = driver.findElement(By.xpath(xpath));
            actions.click(element).perform();
            driver.wait(1000);
        }catch (Exception e){
            logger.info(AmazonBasicTests.class.getName()+ e.getLocalizedMessage());
            LogUtil.logStackTrace(e, logger);
        }

    }
}
