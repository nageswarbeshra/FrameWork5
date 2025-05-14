import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import org.example.utils.CommonAppiumUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class Listeners extends CommonAppiumUtils implements ITestListener {

    ExtentReports extent = ExtentReporterNG.config();
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.INFO, "Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());

        AppiumDriver driver = null;
        try {
            if (result.getInstance() instanceof BaseTest testInstance) {
                driver = testInstance.getDriver();
            }
            if (driver != null) {
                test.addScreenCaptureFromPath(screenshotPath(result.getMethod().getMethodName(), driver),
                        result.getMethod().getMethodName());
                test.log(Status.INFO, "Screenshot captured for failed test.");
            } else {
                test.log(Status.WARNING, "Driver is null, screenshot not captured.");
            }
        } catch (IOException e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        } catch (Exception e) {
            test.log(Status.WARNING, "Error accessing driver: " + e.getMessage());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test PASSED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}