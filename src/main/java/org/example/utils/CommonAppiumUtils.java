package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class CommonAppiumUtils {
    public Double getFormattedAmount(String amount)
    {
        Double price = Double.parseDouble(amount.substring(1));
        return price;
    }

    public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
        // Validate input
        if (jsonFilePath == null || jsonFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON file path cannot be null or empty");
        }

        // Check if file exists
        File file = new File(jsonFilePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File does not exist or is not a valid file: " + jsonFilePath);
        }

        try {
            // Read file content
            String jsonContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<HashMap<String, String>> data = mapper.readValue(jsonContent,
                    new TypeReference<List<HashMap<String, String>>>(){});

            return data;
        } catch (com.fasterxml.jackson.core.JsonParseException e) {
            throw new IOException("Invalid JSON format in file: " + jsonFilePath, e);
        } catch (com.fasterxml.jackson.databind.JsonMappingException e) {
            throw new IOException("JSON structure does not match expected format (List<HashMap<String, String>>): " + jsonFilePath, e);
        }
    }


    public void waitForElementToAppear(WebElement ele,AppiumDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains((ele),"text","cart"));

    }
    public static String timeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String currentTime1 = now.format(formatter);
        return currentTime1; // Returns date and time in ISO format (e.g., 2025-05-06T19:43:25.123)
    }

    public String screenshotPath(String testCaseName, AppiumDriver driver) throws IOException {

        String currentTime = timeStamp();

        File source = driver.getScreenshotAs(OutputType.FILE);
        String screenshotPath=System.getProperty("user.dir")+"//reports//"+testCaseName+"_"+currentTime+".png";
        FileUtils.copyFile(source,new File(screenshotPath));
        return screenshotPath;



    }


}
