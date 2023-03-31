package testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DataFormatUtil;
import utils.RandomData;


public class HomePageTest extends BaseTest{

// Validate if single item is added to the todolist
    @Test
    public void testAddSingleItemToTheList(){
        String textToEnter = RandomData.getItemName();
        softAssert.assertTrue(homePage.isTodoLabelVisible());
        softAssert.assertTrue(homePage.checkVisibilityOfFooterElement());
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.isItemAddedToTodoList(textToEnter));
        softAssert.assertTrue(homePage.checkVisibilityOfToggleButton(textToEnter));
        softAssert.assertTrue(homePage.checkVisibilityOfCancelButton(textToEnter));
        softAssert.assertTrue(homePage.validateCountOfItemsLeft());

    }

// Validate updating text of existing todolist item
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

// Validate items can be removed from todolist
    @Test
    public void testRemoveItemFromList(){
        String initialText = RandomData.getItemName();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(initialText);
        homePage.isItemAddedToTodoList(initialText);
        homePage.removeElementFromTodoList(initialText);
        softAssert.assertTrue(homePage.validateAbsenceOfItemFromList(initialText));
    }

// Validate items can be marked as completed and can be removed from the list
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

// Validate empty string can't be added to the list
    @Test
    public void testAddingEmptyString(){
        String textToEnter = " ";
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.validateAbsenceOfItemFromList(textToEnter));
    }

// Validate surrounding spaces from string removed post addition to list
    @Test
    public void testAddingStringWithTrailingSpaces(){
        String textToEnter = RandomData.getItemWithSpaces();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.verifySpacesRemovedFromListItem(textToEnter));
    }

// Validate adding multiple items to the list and its sequence
    @Test(dataProvider = "GetListOFRandomData")
    public void testAdditionOfMultipleItemsAndSequence(String data[]){
        homePage.enterListOfTodoItems(data);
        softAssert.assertTrue(homePage.validateSequenceOfTodoItems(data));
    }

// Validate multiple items can be added and removed from the list at a time
    @Test(dataProvider = "GetDataFromExcel")
    public void testAddMultipleItemsAndMarkMultipleAsDone(String todoItems, String todoStatus){
        String[] todoItemStatus = DataFormatUtil.convertToStringArray(todoStatus);
        String[] todoItemsData= DataFormatUtil.convertToStringArray(todoItems);
        homePage.enterListOfTodoItems(todoItemsData);
        int countOfInProgress = homePage.markMultipleItemsAsComplete(todoItemsData, todoItemStatus);
        softAssert.assertTrue(homePage.validateCountOfItemsLeft(countOfInProgress));
        homePage.clickOnClearCompleted();
        softAssert.assertTrue(homePage.validateInProgressItems(todoItemStatus, todoItemsData));
    }

// Validate contents of todolist retained if page is refreshed
    @Test
    public void testToDoListContentPostPageRefresh(){
        String textToEnter = RandomData.getItemName();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.isItemAddedToTodoList(textToEnter));
        homePage.refreshHomePage();
        homePage.isTodoLabelVisible();
        softAssert.assertTrue(homePage.isItemAddedToTodoList(textToEnter));
    }

// Validate contents of todolist retained if page is refreshed
    @Test
    public void testToDoListAllowsDuplicateEntries(){
        String textToEnter = RandomData.getItemName();
        homePage.isTodoLabelVisible();
        homePage.enterSingleTodoItem(textToEnter);
        homePage.enterSingleTodoItem(textToEnter);
        softAssert.assertTrue(homePage.isItemAddedToTodoList(textToEnter));
        softAssert.assertTrue(homePage.validateCountOfItemsLeft(textToEnter, 2));
    }

}
