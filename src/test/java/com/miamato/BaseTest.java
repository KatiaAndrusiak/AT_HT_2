package com.miamato;


import com.miamato.listeners.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

@Listeners({TestResultsListener.class,TestReporter.class})
public abstract class BaseTest {

    public static WebDriver driver;
    static final String CHROME_DRIVER_PATH = "drivers/windows/chromedriver.exe";
    public static final Logger logger = LogManager.getLogger("");
    public SoftAssert softAssert = new SoftAssert();


    @BeforeClass
    public void setup(){
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
    }

    @AfterClass
    public void cleanUp(){
        driver.quit();
    }
}