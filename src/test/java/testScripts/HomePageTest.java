package testScripts;

import org.testng.annotations.Test;
import utils.RandomData;

public class HomePageTest extends BaseTest{

    @Test
    public void testAddSingleItemToTheList(){
        String textToEnter = RandomData.getItemName();
        softAssert.assertTrue(homePage.isTodoLabelVisible());
        softAssert.assertTrue(homePage.isTextFieldHavingFocus());
        softAssert.assertTrue(homePage.checkVisibilityOfFooterElement());
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.isItemAddedToTodoList(textToEnter));
        softAssert.assertTrue(homePage.checkVisibilityOfToggleButton(textToEnter));
        softAssert.assertTrue(homePage.checkVisibilityOfCancelButton(textToEnter));
        softAssert.assertTrue(homePage.validateCountOfItemsLeft());
    }

    @Test
    public void testEditTextOfItemPresentInList(){
        String initialText = RandomData.getItemName();
        String updatedText = RandomData.getItemName();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(initialText);
        homePage.isItemAddedToTodoList(initialText);
        homePage.modifyItemFromTodoList(initialText, updatedText);
        softAssert.assertTrue(homePage.isItemAddedToTodoList(updatedText));
    }

    @Test
    public void testRemoveItemFromList(){
        String initialText = RandomData.getItemName();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(initialText);
        homePage.isItemAddedToTodoList(initialText);
        homePage.removeElementFromTodoList(initialText);
        softAssert.assertTrue(homePage.validateAbsenceOfItemFromList(initialText));
    }

    @Test
    public void testMarkItemAsCompletedAndClearFromList(){
        String initialText = RandomData.getItemName();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(initialText);
        homePage.isItemAddedToTodoList(initialText);
        homePage.markElementAsDone(initialText);
        softAssert.assertTrue(homePage.validateItemIsMarkedAsCompleted(initialText));
        homePage.clickOnClearCompleted();
        softAssert.assertTrue(homePage.validateAbsenceOfItemFromList(initialText));
    }

    @Test
    public void testAddingEmptyString(){
        String textToEnter = " ";
        homePage.isTodoLabelVisible();
        homePage.isTextFieldHavingFocus();
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.isTextFieldHavingFocus());
        softAssert.assertTrue(homePage.validateAbsenceOfItemFromList(textToEnter));
    }

    @Test
    public void testAddingStringWithTrailingSpaces(){
        String textToEnter = RandomData.getItemWithSpaces();
        homePage.isTodoLabelVisible();
        homePage.isTextFieldHavingFocus();
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.verifySpacesRemovedFromListItem(textToEnter));
    }

    @Test(dataProvider = "GetListOFRandomData")
    public void testAdditionOfMultipleItemsAndSequence(Object data[]){
        homePage.enterListOfTodoItems(data);
        softAssert.assertTrue(homePage.validateSequenceOfTodoItems(data));
    }

    @Test(dataProvider = "GetDataFromExcel")
    public void testAddMultipleItemsAndRemoveMultiple(String todoItems, String todoStatus){
        System.out.println(todoItems);
        System.out.println(todoStatus);

    }
}
