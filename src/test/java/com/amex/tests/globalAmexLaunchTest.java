package com.amex.tests;

import com.amex.pages.landingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class globalAmexLaunchTest {
    private WebDriver driver;
    private String Country_url;

    @BeforeTest
    public void setupDriver(){
        //set chromedriver path
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\683132\\OneDrive - Cognizant\\chromedriver109\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }
    @Test
    @Parameters({"Country"})
    public void testGotoAmexSite( @Optional("US") String Country) throws InterruptedException {
        landingPage landingPage = new landingPage(driver);
        //landingPage.launchAmexWebsite("https://www.americanexpress.com/us/");
        this.Country_url = landingPage.decideURL(Country);
        //landingPage.launchAmexWebsite(landingPage.decideURL(Country));
        landingPage.launchAmexWebsite(this.Country_url);
        Thread.sleep(3000);
    }
    @Test(dependsOnMethods = "testGotoAmexSite")
    public void testLandingPage() {
        landingPage landingPage = new landingPage(driver);
        landingPage.verifyLabelPersonalCards(this.Country_url);
    }
    @AfterTest
    public void tearDown(){
        this.driver.quit();
    }

}