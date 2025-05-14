import org.example.pageObjects.android.CartPage;
import org.example.pageObjects.android.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.example.utils.AndroidActions.keybutton;

public class TestRunner2 extends BaseTest{




    @Test
    public void MobileTest2() throws URISyntaxException, MalformedURLException, InterruptedException {


//        formPage.setCountry("India");

        formPage.setNameField("Nageswar Beshr");
        formPage.setGender("female");
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
    }
}
