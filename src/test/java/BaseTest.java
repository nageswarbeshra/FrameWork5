import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.example.pageObjects.android.FormPage;
import org.example.utils.CommonAppiumUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.*;
import java.net.*;
import java.time.Duration;

public class BaseTest extends CommonAppiumUtils {

    private static AppiumDriverLocalService appiumService;
    protected AppiumDriver driver;
    protected FormPage formPage;
    private static final String APPIUM_IP = Utils.getIPAddress();
    private static final int APPIUM_PORT = Integer.parseInt(System.getProperty("appium.port", "4723"));
    private static final String userName = Utils.getUsername();
    private static final String serialNumber = Utils.getSerialNumberViaAdb();

    @BeforeClass(alwaysRun = true)
    public synchronized void startAppiumServer() throws IOException {
        if (appiumService == null || !appiumService.isRunning()) {
            String appiumMainJsPath = "C:/Users/" + userName + "/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
            File appiumMainJs = new File(appiumMainJsPath);
            if (!appiumMainJs.exists()) {
                throw new RuntimeException("Appium main.js not found at: " + appiumMainJsPath);
            }

            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress(APPIUM_IP)
                    .usingPort(APPIUM_PORT)
                    .withTimeout(Duration.ofSeconds(30))
                    .withAppiumJS(appiumMainJs)
                    .withLogFile(new File("appium_" + APPIUM_PORT + ".log"))
                    .withArgument(() -> "--log-level", "debug");

            appiumService = AppiumDriverLocalService.buildService(builder);
            appiumService.start();

            int maxAttempts = 10;
            int attempt = 0;
            while (attempt < maxAttempts && !appiumService.isRunning()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                attempt++;
            }

            if (appiumService.isRunning()) {
                System.out.println("Appium server started at: " + appiumService.getUrl());
            } else {
                throw new RuntimeException("Appium server failed to start. Check appium_" + APPIUM_PORT + ".log.");
            }
        }
    }

    @BeforeMethod
    public void setUp() {
        try {
            System.out.println("Device Serial Number: " + serialNumber);
            if (serialNumber.isEmpty()) {
                throw new RuntimeException("No device serial number found. Ensure a device is connected via ADB.");
            }

            UiAutomator2Options options = new UiAutomator2Options()
                    .setUdid(serialNumber)
                    .setAppPackage("com.androidsample.generalstore")
                    .setAppActivity("com.androidsample.generalstore.MainActivity")
                    .setAutomationName("UiAutomator2")
                    .setNoReset(true)
                    .setFullReset(false);

            driver = new AndroidDriver(new URI("http://" + APPIUM_IP + ":" + APPIUM_PORT).toURL(), options);
            formPage = new FormPage((AndroidDriver) driver);
            System.out.println("Driver initialized: " + driver);
        } catch (Exception e) {
            System.err.println("Failed to initialize driver: " + e.getMessage());
            throw new RuntimeException("Failed to initialize Appium driver", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver quit successfully.");
        }
    }

    @AfterClass(alwaysRun = true)
    public synchronized void stopAppiumServer() {
        if (appiumService != null && appiumService.isRunning()) {
            appiumService.stop();
            System.out.println("Appium server stopped successfully.");
        }
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public static class Utils {
        public static String getIPAddress() {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return "127.0.0.1"; // Fallback to localhost
            }
        }

        public static String getUsername() {
            return System.getProperty("user.name");
        }

        public static String getSerialNumberViaAdb() {
            try {
                Process process = Runtime.getRuntime().exec("adb shell getprop ro.serialno");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String serialNumber = reader.readLine();
                reader.close();
                return serialNumber != null ? serialNumber : "";
            } catch (Exception e) {
                return "";
            }
        }
    }
}