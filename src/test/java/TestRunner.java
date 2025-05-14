
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.Activity;
import org.example.pageObjects.android.CartPage;
import org.example.pageObjects.android.ProductCatalogue;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import static org.example.utils.AndroidActions.keybutton;


public class TestRunner extends BaseTest{
//

    @Test(dataProvider = "getData")
    public void MobileTest(HashMap<String,String> input) throws URISyntaxException, MalformedURLException, InterruptedException {


        formPage.setCountry(input.get("country"));

        formPage.setNameField(input.get("name"));
        formPage.setGender(input.get("gender"));
        ProductCatalogue productCatalogue = formPage.pressShopButton();
        Thread.sleep(4000);
        productCatalogue.addItemsTocart(0);
        productCatalogue.addItemsTocart(0);
        CartPage cartpage = productCatalogue.goToCart();
        Thread.sleep(4000);
        double productsum = cartpage.getproductSum();
        double displaysum = cartpage.getTotalAmountDisplayed();
        Assert.assertEquals(productsum, displaysum);
        Thread.sleep(4000);
        cartpage.accetTermCondition();
//        cartpage.processButton();
        keybutton("BACK");
        keybutton("BACK");
        Thread.sleep(4000);

        // keybutton("BACK"); //". Must be a valid AndroidKey enum value (e.g., BACK, HOME).


//    }
//    @Test
//    public void Fillform() throws URISyntaxException, MalformedURLException, InterruptedException {
//
//
////        formPage.setCountry("India");
//
//        formPage.setNameField("Nageswar Beshr");
//        formPage.setGender("female");
//        ProductCatalogue productCatalogue = formPage.pressShopButton();
//        Thread.sleep(4000);
//        productCatalogue.addItemsTocart(0);
//        productCatalogue.addItemsTocart(0);
//        CartPage cartpage = productCatalogue.goToCart();
//        Thread.sleep(4000);
//        double productsum = cartpage.getproductSum();
//        double displaysum  =cartpage.getTotalAmountDisplayed();
//        Assert.assertEquals(productsum,displaysum);
//        Thread.sleep(4000);
//        cartpage.accetTermCondition();
////        cartpage.processButton();
//
//        driver.quit();
//    }
    }
    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String,String>> data = getJsonData(System.getProperty("user.dir")+"\\src\\main\\java\\org\\example\\testData\\eCommerce.json");
        return new Object[][] {{data.get(0)},{data.get(1)},{data.get(2)}};
    }
    @BeforeMethod
    public void preSetup()
    {
        formPage.setActivity();
    }




}