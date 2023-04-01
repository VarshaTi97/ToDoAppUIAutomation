package base;

import constants.Filepath;
import constants.LocatorType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reporting.ExtentManager;
import utils.PropertyFileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

//    Selenium wrapper class for all the built-in methods.
public class PredefinedActions {


    protected static RemoteWebDriver driver;
    private static WebDriverWait wait;

    protected void startBrowser(String browserName, String env) {
        /*
        * initiates driver for local execution and launches URL
        * param browserName: defines browser name
        * param env: name of the env (QA/Prod/Stage)
        * return: void
        * */
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Browser case not matched..." + browserName);
        }

        driver.manage().window().maximize();
        driver.get(getApplicationURL(env));
        wait = new WebDriverWait(driver, 20);
        ExtentManager.log("STEP: Browser launched");
    }

    protected void startRemoteBrowser(String browserName, String env) throws MalformedURLException {
        /*
         * initiates driver for remote execution and launches URL
         * param browserName: defines browser name
         * param env: name of the env (QA/Prod/Stage)
         * return: void
         * */
        URL url = new URL("http://localhost:4444/wd/hub");
        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("headless", true);
                driver = new RemoteWebDriver(url, chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("headless", true);
                driver = new RemoteWebDriver(url, firefoxOptions);
                break;
            default:
                throw new RuntimeException("Browser case not matched..." + browserName);
        }

        driver.manage().window().maximize();
        driver.get(getApplicationURL(env));
        wait = new WebDriverWait(driver, 20);
        ExtentManager.log("STEP: Browser launched");
    }

    protected WebElement getElementWithoutWait(String locatorType, String locatorValue){
        /*
         * returns matching web element without applying explicit wait
         * param locatorType: defines locator strategy to be used
         * param locatorValue: value of the locator
         * return: matching web element
         * */
        WebElement element = null;
        switch(locatorType) {
            case LocatorType.XPATH:
                element  = driver.findElement(By.xpath(locatorValue));
                break;
            case LocatorType.ID:
                element  = driver.findElement(By.id(locatorValue));
                break;
            case LocatorType.CLASS_NAME:
                element  = driver.findElement(By.className(locatorValue));
                break;
            case LocatorType.TAG_NAME:
                element  = driver.findElement(By.tagName(locatorValue));
                break;
            case LocatorType.LINK_TEXT:
                element  = driver.findElement(By.linkText(locatorValue));
                break;
            case LocatorType.PARTIAL_LINK_TEXT:
                element  = driver.findElement(By.partialLinkText(locatorValue));
                break;
            case LocatorType.CSS_SELECTOR:
                element  = driver.findElement(By.cssSelector(locatorValue));
                break;
        }
        return element;
    }

    protected List<WebElement> getListOfElementsWithoutWait(String locatorType, String locatorValue){
        /*
         * returns list of matching web elements without applying explicit wait
         * param locatorType: defines locator strategy to be used
         * param locatorValue: value of the locator
         * return: list of matching web elements
         * */
        List<WebElement> element = null;
        switch(locatorType) {
            case LocatorType.XPATH:
                element  = driver.findElements(By.xpath(locatorValue));
                break;
            case LocatorType.ID:
                element  = driver.findElements(By.id(locatorValue));
                break;
            case LocatorType.CLASS_NAME:
                element  = driver.findElements(By.className(locatorValue));
                break;
            case LocatorType.TAG_NAME:
                element  = driver.findElements(By.tagName(locatorValue));
                break;
            case LocatorType.LINK_TEXT:
                element  = driver.findElements(By.linkText(locatorValue));
                break;
            case LocatorType.PARTIAL_LINK_TEXT:
                element  = driver.findElements(By.partialLinkText(locatorValue));
                break;
            case LocatorType.CSS_SELECTOR:
                element  = driver.findElements(By.cssSelector(locatorValue));
                break;
        }
        return element;
    }

    private WebElement getElementWithWait(String locatorType, String locatorValue){
        /*
         * returns matching web element, waiting until timeout
         * param locatorType: defines locator strategy to be used
         * param locatorValue: value of the locator
         * return: matching web element
         * */
        WebElement element = null;
        switch(locatorType.toLowerCase(Locale.ROOT)) {
            case LocatorType.XPATH:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                break;
            case LocatorType.ID:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                break;
            case LocatorType.CLASS_NAME:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
                break;
            case LocatorType.TAG_NAME:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
                break;
            case LocatorType.LINK_TEXT:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
                break;
            case LocatorType.PARTIAL_LINK_TEXT:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
                break;
            case LocatorType.CSS_SELECTOR:
                element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
                break;
            default:
                System.out.println("locator not found");
        }
        return element;
    }

    public List<WebElement> getListOfElementsWithWait(String locatorType, String locatorValue) {
        /*
         * returns list of matching web elements, waiting until timeout
         * param locatorType: defines locator strategy to be used
         * param locatorValue: value of the locator
         * return: matching list of web elements
         * */
        List<WebElement> listElement = null;
        switch (locatorType) {
            case LocatorType.XPATH:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locatorValue)));
                break;
            case LocatorType.ID:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locatorValue)));
                break;
            case LocatorType.CLASS_NAME:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(locatorValue)));
                break;
            case LocatorType.TAG_NAME:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(locatorValue)));
                break;
            case LocatorType.LINK_TEXT:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(locatorValue)));
                break;
            case LocatorType.PARTIAL_LINK_TEXT:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(locatorValue)));
                break;
            case LocatorType.CSS_SELECTOR:
                listElement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(locatorValue)));
                break;
        }
        ExtentManager.log("STEP: getting list of web elements");
        return listElement;
    }

    private ArrayList<String> getLocatorDetails(String locator){
        /*
         * returns list with detail about locator strategy and locator value
         * param locator: locator from property file
         * return: list with locator type and locator value
         * */
        String elementData[] = locator.split("]:-");
        String locatorType = elementData[0].substring(1);
        String locatorValue = elementData[1];
        ArrayList<String> locatorData = new ArrayList<String>();
        locatorData.add(locatorType);
        locatorData.add(locatorValue);
        return locatorData;
    }

    private WebElement getElement(String locator, boolean isWaitRequired){
        /*
         * returns matching web element , waiting for timeout as mentioned in func argument.
         * param locator: locator from property file
         * param isWaitRequired: wait required or not
         * return: matching web element
         * */
        WebElement element = null;
        ArrayList<String> locatorDetails = getLocatorDetails(locator);
        if(isWaitRequired)
            element = getElementWithWait(locatorDetails.get(0), locatorDetails.get(1));
        else
            element = getElementWithoutWait(locatorDetails.get(0), locatorDetails.get(1));
        return element;
    }

    protected List<WebElement> getListOfElements(String locator, boolean isWaitRequired){
        /*
         * returns list of matching web elements
         * param locator: locator from property file
         * param isWaitRequired: wait required or not
         * return: matching list of web elements
         * */
        List<WebElement> element = null;
        ArrayList<String> locatorDetails = getLocatorDetails(locator);
        if(isWaitRequired)
            element = getListOfElementsWithWait(locatorDetails.get(0), locatorDetails.get(1));
        else
            element = getListOfElementsWithoutWait(locatorDetails.get(0), locatorDetails.get(1));
        return element;
    }

    protected List<Object> getTextOfListOfElements(List<WebElement> elements){
        /*
         * returns list of data present in the web elements
         * param elements: list of web elements
         * return: list of text present in elements
         * */
        List<Object> data = new ArrayList<Object>();
        for(WebElement element: elements){
            scrollTillElement(element);
            data.add(element.getText());
        }
        return data;
    }

    private String getApplicationURL(String env) {
        /*
         * returns application url based on env
         * param env: env on which script to be executed
         * return: String
         * */
        return PropertyFileReader.getValue(Filepath.CONFIG_FILE_PATH, env.toLowerCase()+"_url");
    }

    protected void closeBrowser() {
        /*
         * closes all the open tabs
         * param: None
         * return: void
         * */
        driver.quit();
    }

    protected void refreshPage(){
        /*
         * refresh the current tab
         * param: None
         * return: void
         * */
        driver.navigate().refresh();
    }

    protected void enterText(String locator, String textToSet, boolean isWaitRequired){
        /*
         * enters text in the input field
         * param locator: locator from property file
         * param textToSet: text to set in text field
         * param isWaitRequired: accepts explicit wait value
         * return: void
         * */
        WebElement element = getElement(locator,isWaitRequired);
        element.clear();
        element.sendKeys(textToSet);
        ExtentManager.log("STEP: value " + textToSet + " set on locator " + element);
    }

    protected void clickOnElement(String locator, boolean isWaitRequired){
        /*
         * clicks on element
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: void
         * */
        WebElement element= getElement(locator,isWaitRequired);
        element.click();
        ExtentManager.log("STEP: clicked on locator " + element);
    }

    protected boolean checkElementIsNotPresent(String locator, boolean isWaitRequired){
        /*
         * checks element is not visible in the DOM
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: boolean
         * */
        return !checkForElementToBeVisible(locator, isWaitRequired);
    }

    protected boolean checkForElementToBeVisible(String locator, boolean isWaitRequired) {
        /*
         * checks element is not visible in the DOM
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: boolean
         * */
        ExtentManager.log("STEP: Checking for visibility: " + locator);
        List<WebElement> elements  = getListOfElements(locator, isWaitRequired);
        return elements.size() != 0 ? true :false;
    }

    protected void pressEnterKey(){
        /*
         * press enter key from keyboard
         * param: None
         * return: void
         * */
        ExtentManager.log("STEP: Clicked on Enter button");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).build().perform();
    }

    protected void mouseHoverOnElement(String locator, boolean isWaitRequired) {
        /*
         * moves mouse pointer to element
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: void
         * */
        WebElement element= getElement(locator, isWaitRequired);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    protected void mouseHoverAndClickOnElement(String locator, boolean isWaitRequired) {
        /*
         * moves mouse pointer to element and clicks on it
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: void
         * */
        WebElement element= getElement(locator, isWaitRequired);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    protected void mouseHoverAndDoubleClickOnElement(String locator, boolean isWaitRequired) {
        /*
         * moves mouse pointer to element and double-clicks on it
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: void
         * */
        WebElement element= getElement(locator, isWaitRequired);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).doubleClick().build().perform();
    }

    protected void editText(String locator, String textToEnter, boolean isWaitRequired){
        /*
         * modifies text in text field
         * param locator: locator from property file
         * param isWaitRequired: accepts explicit wait value
         * return: void
         * */
        WebElement element = getElement(locator, isWaitRequired);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value = '';", element);
        element.sendKeys(textToEnter);
    }

    protected String getAttributeValue(String locator, String attributeName, boolean isWaitRequired){
        /*
         * returns attribute value of an element
         * param locator: locator from property file
         * param attributeName: name of the attribute
         * param isWaitRequired: accepts explicit wait value
         * return: String
         * */
        WebElement element = getElement(locator, isWaitRequired);
        return element.getAttribute(attributeName);
    }

    public static String captureScreenShot() {
        /*
         * captures screenshot and returns file in format of Base64
         * param: None
         * return: String
         * */
        TakesScreenshot ts = (TakesScreenshot) driver;
        ExtentManager.log("STEP: screenshot captured");
        return ts.getScreenshotAs(OutputType.BASE64);
    }

    protected void scrollTillElement(WebElement element) {
        /*
         * scroll till element
         * param: element: element to which scroll
         * return: void
         * */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        ExtentManager.log("STEP: scroll to element " + element);
    }
}
