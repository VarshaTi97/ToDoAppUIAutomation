package listeners;

import base.PredefinedActions;
import constants.Filepath;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.PropertyFileReader;

//Listener class
public class TestCaseListeners extends PredefinedActions implements ITestListener{
    String browser, env;

    public void onTestStart(ITestResult result) {
        //Driver initialization and browser launch code
        ExtentManager.createTestCase(result);
        String executionMode = PropertyFileReader.getValue(Filepath.CONFIG_FILE_PATH, "executionType");
        if (executionMode.equals("local"))
            startBrowser(browser, env);
        else if(executionMode.equals("container")){
            try {
                startRemoteBrowser(browser, env);
            }catch(Exception e){
                ExtentManager.log("Browser launch failed:" +e.getMessage());
            }
        }
        //logging to extent report
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
        //read browser name from commandline or config file
        browser = System.getProperty("browserType") == null ?
                PropertyFileReader.getValue(Filepath.CONFIG_FILE_PATH, "browserType") : System.getProperty("browserType");
        // read env from commandline or config file
        env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        String filename = ExtentManager.getReportNameWithTimeStamp();
        //Extent report file name
        String fullPath = System.getProperty("user.dir")+ "/reports/" + filename;
        //Initialization of extent report instance
        ExtentManager.initExtentReports(fullPath, "ToDo App UI Automation Report",
                "UI Test Execution");
    }

    public void onFinish(ITestContext context) {
        ExtentManager.flushReport();
    }

}

