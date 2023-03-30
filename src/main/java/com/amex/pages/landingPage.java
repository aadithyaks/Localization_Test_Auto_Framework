package com.amex.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;

public class landingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private ResourceBundle resourcebundle =null;
    private Locale locale =null;

    @FindBy(xpath = "(//p[contains(@class, 'heading-3 display-block')])[1]")
    private WebElement label_PersonalCards;

    @FindBy(xpath = "//button[text()='Tout Accepter']")
    private  WebElement btn_cookie_accept;

    //constructor
    public landingPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);
    }
    //method_01
    public String decideURL(String Country){
        String url = "https://americanexpress.com/";
        //switch case based on Country value
        switch (Country){
            case"INDIA":
                System.out.println("Country= " + Country);
                url = url.concat("en-in/");
                break;
            case"MEXICO":
                System.out.println("Country= " + Country);
                url = url.concat("es-mx/");
                break;
            case"CANADA":
                System.out.println("Country= " + Country);
                url = url.concat("en-ca/");
                break;
            case"FRANCE":
                System.out.println("Country= " + Country);
                url = url.concat("fr-fr/");
                break;
            default:
                System.out.println("Country= " + "US");
                url = url.concat("us/");
        }
        return url;
    }
    //method_02
    public void launchAmexWebsite(String url){
        this.driver.get(url);
        this.driver.manage().window().maximize();
        this.wait.until(ExpectedConditions.visibilityOf(this.label_PersonalCards));
        //cookies alert only in France
        if (url.equalsIgnoreCase("https://americanexpress.com/fr-fr/")){
            System.out.println("Landed on France site");
            this.wait.until(ExpectedConditions.visibilityOf(this.btn_cookie_accept)).click();
        }
    }
    //method_03
    public void verifyLabelPersonalCards(String url) {
        String label_exp;
        String label_actual;

        label_actual = this.label_PersonalCards.getText();
        System.out.println("label_actual= " + label_actual);

        if (url.endsWith("en-ca/")) {
            resourcebundle = PropertyResourceBundle.getBundle("Translate", new Locale("en", "CA"));
        } else if(url.endsWith("fr-fr/")) {
            resourcebundle = PropertyResourceBundle.getBundle("Translate",  new Locale("fr", "FR"));
            //resourcebundle = PropertyResourceBundle.getBundle("Translate",  Locale.FRANCE);
        }else if(url.endsWith("en-in/")) {
            resourcebundle = PropertyResourceBundle.getBundle("Translate", new Locale( "en", "IN"));
        }else if(url.endsWith("es-mx/")) {
            resourcebundle = PropertyResourceBundle.getBundle("Translate", new Locale("es", "MX"));
        }else {
            resourcebundle = PropertyResourceBundle.getBundle("Translate", new Locale("en", "US"));
        }
        System.out.println("Locale= " + resourcebundle.getLocale().toString());
        label_exp = resourcebundle.getString("label_exp_PersonalCards_001");
        //get rid of double quotes in the beginning and at the end
        label_exp = label_exp.replaceAll("^\"|\"$", "");
        System.out.println("label_exp value = " + label_exp);

        boolean b = label_actual.equalsIgnoreCase(label_exp);
        System.out.println("boolean b= " + b);
        assert b : "Failed. Expected label " + label_exp +" but displayed is "+label_actual+" on page.";

    }
}
