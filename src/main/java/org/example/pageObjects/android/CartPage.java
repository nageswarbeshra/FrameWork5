package org.example.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AndroidActions {

    public AndroidDriver driver;
    public CartPage(AndroidDriver driver)
    {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }


    @AndroidFindBy(id="com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productList;
    @AndroidFindBy(id="com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    @AndroidFindBy(id="com.androidsample.generalstore:id/termsButton")
    private WebElement term;

    @AndroidFindBy(id="android:id/button1")
    private WebElement acceptButton;

    @AndroidFindBy(id="com.androidsample.generalstore:id/btnProceed")
    private WebElement procced;


    public List<WebElement> getProductList()
    {
        return productList;
    }
    public double getproductSum()
    {
        int count = productList.size();
        double totalsum = 0;
        for (WebElement webElement : productList) {
            String amountString = webElement.getText();
            double price = getFormattedAmount(amountString);
            totalsum = totalsum + price;
        }
        return totalsum;

    }
    public Double getTotalAmountDisplayed()
    {
        return getFormattedAmount(totalAmount.getText());
    }
    public void accetTermCondition()
    {
        LongpressActions(term);
        acceptButton.click();
    }
    public void processButton()
    {
        procced.click();
    }


}
