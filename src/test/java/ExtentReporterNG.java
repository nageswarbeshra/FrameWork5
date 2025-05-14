import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.example.utils.CommonAppiumUtils;
import org.testng.annotations.BeforeTest;

public class ExtentReporterNG extends CommonAppiumUtils {

    static ExtentReports extent;
    @BeforeTest
    public static ExtentReports config() {
        // ExtentReports, ExtentSparkReport
        String currentTime = timeStamp();


        String path = System.getProperty("user.dir") + "//reports//"+currentTime+"_index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Automation results");
        reporter.config().setDocumentTitle("Test result");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Nageswar");
        return extent;
    }
}
