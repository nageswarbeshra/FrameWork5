package org.example.pageObjects.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class FormPage extends AndroidActions {
    AndroidDriver driver;
    public FormPage(AndroidDriver driver)
    {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id="com.androidsample.generalstore:id/radioFemale")
    private WebElement genderFemale;

    @AndroidFindBy(id="com.androidsample.generalstore:id/radioMale")
    private WebElement genderMale;

    @AndroidFindBy(id="android:id/text1")
    private WebElement countrySelectOptions;
    @AndroidFindBy(id="com.androidsample.generalstore:id/btnLetsShop")
    private WebElement shopButton;


    public void setNameField(String name)
    {
        nameField.sendKeys(name);
        driver.hideKeyboard();
    }

    public void setGender(String gender)
    {
        if (gender.contains("female"))
            genderFemale.click();
        else
            genderMale.click();
    }

    public void setCountryName(String country)
    {
        nameField.sendKeys(country);
        driver.hideKeyboard();
    }

    public void setCountry(String countryName)

    {
        countrySelectOptions.click();
        scrolToText(countryName);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\""+countryName+"\"]")).click();

    }
    public ProductCatalogue pressShopButton()
    {
        shopButton.click();
        return new ProductCatalogue(driver);
    }
    public void setActivity()
    {
        Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.MainActivity");
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of(
                "intent", "com.androidsample.generalstore/com.androidsample.generalstore.MainActivity"));
    }


}
