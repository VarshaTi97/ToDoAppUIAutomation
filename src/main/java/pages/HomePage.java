package pages;

import base.PredefinedActions;
import constants.Filepath;
import org.openqa.selenium.WebElement;
import reports.ExtentManager;
import utils.PropertyFileReader;
import java.util.List;

// Contains helper methods for functionality of home page
public class HomePage extends PredefinedActions {
    private static HomePage homePage = new HomePage();
    private PropertyFileReader propertyFileReader = null;

    //Singleton design pattern
    private HomePage() {
        propertyFileReader = new PropertyFileReader(Filepath.HOME_PAGE_LOCATORS);
    }

    //returns object of homePage class
    public static HomePage getHomePage() {
        return homePage;
    }

    //Checks if do todolabel is present on screen
    public boolean isTodoLabelVisible() {
        ExtentManager.log("STEP: Validating Todo Label at home screen");
        return checkForElementToBeVisible(propertyFileReader.getValue("todoLabel"), true);
    }

    //Enters item in todolist
    public void enterSingleTodoItem(String text) {
        enterText(propertyFileReader.getValue("inputTextField"), text, true);
        pressEnterKey();
        ExtentManager.log("STEP: User added \'" + text + "\' in the input field");
    }

    //Enters multiple todolist items
    public void enterListOfTodoItems(String items[]) {
        for (String item : items) {
            enterSingleTodoItem(item);
        }
    }

    //Checks if sequence of todolist items are as per insertion order
    public boolean validateSequenceOfTodoItems(String items[]) {
        List<WebElement> elements = getListOfElements(propertyFileReader.getValue("listOfTodoItems"),
                true);
        List<Object> todoText = getTextOfListOfElements(elements);
        for (int i = 0; i < items.length; i++) {
            if (!todoText.get(i).equals(items[i].trim()))
                return false;
        }
        return true;
    }

    //validates if todoitem is added to the list
    public boolean isItemAddedToTodoList(String text) {
        String locator = String.format(propertyFileReader.getValue("textOfTodoItems"), text);
        ExtentManager.log("STEP: Validated \'" + text + "\' added to todo list");
        return checkForElementToBeVisible(locator, true);

    }

    // validates if count of items left is correct
    public boolean validateCountOfItemsLeft() {
        int count_of_items = getListOfElements(propertyFileReader.getValue("listOfTodoItems"), false)
                .size();
        String locator = String.format(propertyFileReader.getValue("countOfItemsLeft"), count_of_items);
        ExtentManager.log("STEP: Validated count of items added to todo list is correct");
        return checkForElementToBeVisible(locator, true);
    }

    // validates if count of items left is matching the supplied count
    public boolean validateCountOfItemsLeft(int count) {
        String locator = String.format(propertyFileReader.getValue("countOfItemsLeft"), count);
        ExtentManager.log("STEP: Validated count of items added to todo list is correct");
        return checkForElementToBeVisible(locator, true);
    }

    // removes element from todolist
    public void removeElementFromTodoList(String itemToBeRemoved) {
        String listElement = String.format(propertyFileReader.getValue("textOfTodoItems"), itemToBeRemoved);
        String cancelButton = String.format(propertyFileReader.getValue("itemCancelButton"), itemToBeRemoved);
        mouseHoverAndClickOnElement(listElement, false);
        mouseHoverAndClickOnElement(cancelButton, false);
        ExtentManager.log("STEP: Removed item \'" + itemToBeRemoved + "\' from the todo list");
    }

    //check element should not be present in the list
    public boolean validateAbsenceOfItemFromList(String item) {
        String element = String.format(propertyFileReader.getValue("textOfTodoItems"), item);
        ExtentManager.log("STEP: Validated item \'" + item + "\' is removed from the todo list");
        return checkElementIsNotPresent(element, false);
    }

    //edits text of the already existing todoitem text with new text
    public void modifyItemFromTodoList(String initialText, String modifiedText) {
        String listItem = String.format(propertyFileReader.getValue("textOfTodoItems"), initialText);
        mouseHoverAndDoubleClickOnElement(listItem, true);
        mouseHoverAndClickOnElement(propertyFileReader.getValue("editItemTextField"), true);
        editText(propertyFileReader.getValue("editItemTextField"), modifiedText, true);
        pressEnterKey();
        ExtentManager.log("STEP: Modified item \'" + initialText + "\' to" + modifiedText + " in the todo list");
    }

    // marks todoitem as done
    public void markElementAsDone(String text) {
        String toggleButtonItem = String.format(propertyFileReader.getValue("itemToggleButton"), text);
        clickOnElement(toggleButtonItem, false);
        ExtentManager.log("STEP: Marked Todo item \'" + text + "\' as done");
    }

    // validates if items are marked as completed or not
    public boolean validateItemIsMarkedAsCompleted(String text) {
        String listItem = String.format(propertyFileReader.getValue("completedItemStatusElement"), text);
        ExtentManager.log("STEP: Validating toggle button for \'" + text + "\' is selected");
        return getAttributeValue(listItem, "class", true).equalsIgnoreCase("completed") ? true : false;
    }

    // clicks on clear completed button
    public void clickOnClearCompleted() {
        clickOnElement(propertyFileReader.getValue("clearCompletedButton"), false);
        ExtentManager.log("STEP: Clicked on clear completed button");
    }

    // verify if string with spaces is entered, surrounding space is removed
    public boolean verifySpacesRemovedFromListItem(String text) {
        ExtentManager.log("STEP: Validated surrounding spaces removed from todo item.");
        return isItemAddedToTodoList(text.trim());
    }

    //checks visibility of footer text
    public boolean checkVisibilityOfFooterElement() {
        return checkForElementToBeVisible(propertyFileReader.getValue("footerTextElement"), true);
    }

    //checks visibility of toggle button for todoitem
    public boolean checkVisibilityOfToggleButton(String text) {
        String toggleButtonItem = String.format(propertyFileReader.getValue("itemToggleButton"), text);
        return checkForElementToBeVisible(toggleButtonItem, false);
    }

    //checks visibility of cancel button for todoitem
    public boolean checkVisibilityOfCancelButton(String text) {
        String listElement = String.format(propertyFileReader.getValue("textOfTodoItems"), text);
        String cancelButton = String.format(propertyFileReader.getValue("itemCancelButton"), text);
        mouseHoverAndClickOnElement(listElement, false);
        return checkForElementToBeVisible(cancelButton, false);
    }

    //marks list of elements as complete
    public int markMultipleItemsAsComplete(String[] todoItem, String[] todoStatus){
        int inProgress = 0;
        for(int i=0;i<todoItem.length;i++){
            if(todoStatus[i].equals("complete")){
                markElementAsDone(todoItem[i]);
            }
            else{
                inProgress++;
            }
        }
        return inProgress;
    }

    //Validates In progress items are visible in DOM
    public boolean validateInProgressItems(String[] todoItem, String[] todoStatus) {
        for (int i = 0; i < todoItem.length; i++) {
            if (todoStatus[i].equals("in_progress")) {
                boolean result = isItemAddedToTodoList(todoItem[i]);
                if (!result)
                    return false;
            }
        }
        return true;
    }

    //refresh home page
    public void refreshHomePage(){
        ExtentManager.log("STEP: Refresh page");
        refreshPage();
    }

    //Validate duplicate items count based on items text
    public boolean validateCountOfItemsLeft(String text, int count) {
        String locator = String.format(propertyFileReader.getValue("textOfTodoItems"), text);
        int actualCount = getListOfElements(locator, false).size();
        ExtentManager.log("STEP: Validated count of duplicate items added to todo list is correct");
        return actualCount == count ? true : false;
    }
}
