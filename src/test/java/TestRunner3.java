import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class TestRunner3 extends BaseTest{


    @Test
    public void MobileTest3() throws URISyntaxException, MalformedURLException, InterruptedException {


//        formPage.setCountry("India");

//        formPage.setNameField("Nageswar Beshr");
        formPage.pressShopButton();
        String actualToast = driver.findElement(By.xpath("//android.widget.Toast[@text=\"Please enter your name\"]")).getText();
        Assert.assertEquals(actualToast,"Please enter name");
    }
    @Test
    public void MobileTest4() throws URISyntaxException, MalformedURLException, InterruptedException {


//        formPage.setCountry("India");

         formPage.setNameField("Nageswar Beshr");
//        formPage.pressShopButton();
        String actualToast = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Select the country where you want to shop\"]")).getText();
        Assert.assertEquals(actualToast,"Select the country where you want  shop");
    }
    @Test
    public void MobileTest5() throws URISyntaxException, MalformedURLException, InterruptedException {


//        formPage.setCountry("India");

//        formPage.setNameField("Nageswar Beshr");
        formPage.pressShopButton();
        String actualToast = driver.findElement(By.xpath("//android.widget.Toast[@text=\"Please enter your name\"]")).getText();
        Assert.assertEquals(actualToast,"Please enter name");
    }
    @Test
    public void MobileTest6() throws URISyntaxException, MalformedURLException, InterruptedException {


//        formPage.setCountry("India");

//        formPage.setNameField("Nageswar Beshr");
        formPage.pressShopButton();
        String actualToast = driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).getText();
        Assert.assertEquals(actualToast,"Let'  Shop");
    }
}
