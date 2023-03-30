package base;

import constants.Filepath;
import constants.LocatorType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reports.ExtentManager;
import utils.PropertyFileReader;
import java.util.*;

public class PredefinedActions {

    protected static WebDriver driver;
    private static WebDriverWait wait;

    protected void startBrowser(String browserName, String env) {
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

    protected WebElement getElementWithoutWait(String locatorType, String locatorValue){
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
        String elementData[] = locator.split("]:-");
        String locatorType = elementData[0].substring(1);
        String locatorValue = elementData[1];
        ArrayList<String> locatorData = new ArrayList<String>();
        locatorData.add(locatorType);
        locatorData.add(locatorValue);
        return locatorData;
    }

    private WebElement getElement(String locator, boolean isWaitRequired){
        WebElement element = null;
        ArrayList<String> locatorDetails = getLocatorDetails(locator);
        if(isWaitRequired)
            element = getElementWithWait(locatorDetails.get(0), locatorDetails.get(1));
        else
            element = getElementWithoutWait(locatorDetails.get(0), locatorDetails.get(1));
        return element;
    }

    protected List<WebElement> getListOfElements(String locator, boolean isWaitRequired){
        List<WebElement> element = null;
        ArrayList<String> locatorDetails = getLocatorDetails(locator);
        if(isWaitRequired)
            element = getListOfElementsWithWait(locatorDetails.get(0), locatorDetails.get(1));
        else
            element = getListOfElementsWithoutWait(locatorDetails.get(0), locatorDetails.get(1));
        return element;
    }

    protected List<Object> getTextOfListOfElements(List<WebElement> elements){
        List<Object> data = new ArrayList<Object>();
        for(WebElement element: elements){
            data.add(element.getText());
        }
        return data;
    }

    private String getApplicationURL(String env) {
        return PropertyFileReader.getValue(Filepath.CONFIG_FILE_PATH, env.toLowerCase()+"_url");
    }

    protected void closeBrowser() {
        driver.quit();
    }

    protected void enterText(String locator, String textToSet, boolean isWaitRequired){
        WebElement element = getElement(locator,isWaitRequired);
        element.clear();
        element.sendKeys(textToSet);
        ExtentManager.log("STEP: value " + textToSet + " set on locator " + element);
    }

    protected void clickOnElement(String locator, boolean isWaitRequired){
        WebElement element= getElement(locator,isWaitRequired);
        element.click();
        ExtentManager.log("STEP: clicked on locator " + element);
    }

    protected boolean checkElementIsNotPresent(String locator, boolean isWaitRequired){
        List<WebElement> elements  = getListOfElements(locator, isWaitRequired);
        return elements.size() == 0 ? true :false;
    }

    protected boolean isElementFocus(String locator, boolean isWaitRequired){
        WebElement element = getElement(locator, isWaitRequired);
        return element.equals(driver.switchTo().activeElement());
    }

    protected boolean checkForElementToBeVisible(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        return element.isDisplayed();
    }

    protected void pressEnterKey(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).build().perform();
    }

    protected void mouseHoverOnElement(String locator, boolean isWaitRequired) {
        WebElement element= getElement(locator, isWaitRequired);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    protected void mouseHoverAndClickOnElement(String locator, boolean isWaitRequired) {
        WebElement element= getElement(locator, isWaitRequired);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    protected void mouseHoverAndDoubleClickOnElement(String locator, boolean isWaitRequired) {
        WebElement element= getElement(locator, isWaitRequired);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).doubleClick().build().perform();
    }

    protected void editText(String locator, String textToEnter, boolean isWaitRequired){
        WebElement element = getElement(locator, isWaitRequired);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value = '';", element);
        element.sendKeys(textToEnter);
    }

    protected String getAttributeValue(String locator, String attributeName, boolean isWaitRequired){
        WebElement element = getElement(locator, isWaitRequired);
        return element.getAttribute(attributeName);
    }

    public static String captureScreenShot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        ExtentManager.log("STEP: screenshot captured");
        return ts.getScreenshotAs(OutputType.BASE64);
    }

}
