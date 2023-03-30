package listeners;

import base.PredefinedActions;
import constants.Filepath;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.PropertyFileReader;

public class TestCaseListeners extends PredefinedActions implements ITestListener{
    String browser, env;

    public void onTestStart(ITestResult result) {
        ExtentManager.createTestCase(result);
        startBrowser(browser, env);
        ExtentManager.log("STEP: Test Case about to start " + result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        ExtentManager.testSuccess(result);
        closeBrowser();
    }

    public void onTestFailure(ITestResult result) {
        ExtentManager.testFail(result);
        closeBrowser();
    }

    public void onTestSkipped(ITestResult result) {
        ExtentManager.testSkip(result);
        closeBrowser();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
    }

    public void onStart(ITestContext context) {
        browser = System.getProperty("browserType") == null ?
                PropertyFileReader.getValue(Filepath.CONFIG_FILE_PATH, "browserType") : System.getProperty("browserType");
        env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        String filename = ExtentManager.getReportNameWithTimeStamp();
        String fullPath = System.getProperty("user.dir")+ "/reports/" + filename;
        ExtentManager.initExtentReports(fullPath, "ToDo App UI Automation Report",
                "UI Test Execution");
    }

    public void onFinish(ITestContext context) {
        ExtentManager.flushReport();
    }

}

