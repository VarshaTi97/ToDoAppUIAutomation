package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//Listener Class to re-execute the failed testcases
public class RetryAnalyzer implements IRetryAnalyzer {

    private int maxRetry = 3;
    private int retry = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (maxRetry > retry) {
            retry++;
            return true;
        }
        return false;
    }
}
