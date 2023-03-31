package testScripts;

import constants.Filepath;
import lombok.Data;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import utils.ExcelUtility;
import utils.RandomData;

//Parent class of all the test classes
public class BaseTest {
    protected HomePage homePage;
    protected SoftAssert softAssert;

    @BeforeSuite
    public void setUp() {
        homePage = HomePage.getHomePage();
        softAssert = new SoftAssert();
    }

    @AfterSuite
    public void tearDown(){
        softAssert.assertAll();
    }

    @DataProvider(name = "GetListOFRandomData")
    public Object[][] getData(){
        return new Object[][]{RandomData.getListOfRandomItems()};
    }

    @DataProvider(name = "GetDataFromExcel")
    public Object[][] getDataFromExcel(){
        return ExcelUtility.getData(Filepath.TEST_DATA_FILE_PATH, "TestData");
    }
}
