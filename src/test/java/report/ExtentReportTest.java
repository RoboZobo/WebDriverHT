package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static util.DateTimeConvertor.getCurrentDateForReportName;

public class ExtentReportTest {
    public ExtentReports extent;

    @BeforeSuite
    public void setUp() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/" + getCurrentDateForReportName() + "_ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }
}
