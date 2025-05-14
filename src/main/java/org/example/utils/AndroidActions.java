package org.example.utils;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class AndroidActions extends CommonAppiumUtils {
    static AndroidDriver driver;
    public AndroidActions(AndroidDriver driver)
    {

        AndroidActions.driver =driver;
    }


    public void LongpressActions(WebElement ele) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) ele).getId());
        params.put("duration", 3000);
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", params);
    }

    public void scrollToEndAction() {
        ((JavascriptExecutor) driver).executeScript("mobile: scroll", ImmutableMap.of(
                "strategy", "android uiautomator",
                "selector", "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(1)"
        ));
    }
    public void swipAction(WebElement ele, String direction) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) ele).getId(),
                "direction", direction.toLowerCase(),
                "percent", 0.75
        ));
    }
    public void scrolToText(String text)
    {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
    }

    public static void keybutton(String key) {
        try {
            // Convert string to AndroidKey enum
            AndroidKey androidKey = AndroidKey.valueOf(key.toUpperCase());
            ((AndroidDriver) driver).pressKey(new KeyEvent(androidKey));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid key: " + key + ". Must be a valid AndroidKey enum value (e.g., BACK, HOME).");
        }
    }

}
