package reporting;

import base.PredefinedActions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import utils.DateUtility;

//class to handle extent reporting
public class ExtentManager {

    private static final ExtentReports extentReports = new ExtentReports();
    private static ExtentTest extentTest;
    public static void initExtentReports(String fileName, String reportName, String documentTitle, String executionEnv,
                                         String browserName, String platform) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);
        reporter.config().setReportName("Reporter : " + System.getProperty("user.name"));
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setReportName(reportName);
        reporter.config().setDocumentTitle(documentTitle);
        reporter.config().setEncoding("UTF-8");
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Author", System.getProperty("user.name").toUpperCase());
        extentReports.setSystemInfo("Execution Env", executionEnv);
        extentReports.setSystemInfo("Browser", browserName);
        extentReports.setSystemInfo("Execution Type", platform);
        extentReports.attachReporter(reporter);
    }

    //generate report name with current time stamp
    public static String getReportNameWithTimeStamp(){
        return "Test_Execution_Report_" + DateUtility.getCurrentTimestamp() + ".html";
    }

    public static void flushReport() {
        extentReports.flush();
    }

    public static ExtentTest getCurrentTest() {
        return extentTest;
    }

    public static void createTestCase(ITestResult result) {
        extentTest = extentReports.createTest(result.getMethod().getMethodName()).createNode("Test Case Steps");
    }

    public static void testSuccess(ITestResult result) {
        extentTest.log(Status.PASS, MarkupHelper.createLabel("Test Case Executed Successfully", ExtentColor.GREEN));
    }

    public static void testSkip(ITestResult result) {
        extentTest.addScreenCaptureFromBase64String(PredefinedActions.captureScreenShot());
        extentTest.log(Status.SKIP, MarkupHelper.createLabel("Test Case Skipped", ExtentColor.YELLOW));
        extentTest.log(Status.SKIP, result.getThrowable());
    }

    public static void testFail(ITestResult result) {
        extentTest.addScreenCaptureFromBase64String(PredefinedActions.captureScreenShot());
        extentTest.log(Status.FAIL, MarkupHelper.createLabel("Test Case Failed", ExtentColor.RED));
        extentTest.log(Status.FAIL, result.getThrowable());
    }

    public static void log(String message) {
        extentTest.info(message);
    }

}
