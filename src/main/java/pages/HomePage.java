package pages;

import base.PredefinedActions;
import constants.Filepath;
import org.openqa.selenium.WebElement;
import reports.ExtentManager;
import utils.PropertyFileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends PredefinedActions {
    private static HomePage homePage = new HomePage();
    private PropertyFileReader propertyFileReader = null;

    private HomePage() {
        propertyFileReader = new PropertyFileReader(Filepath.HOME_PAGE_LOCATORS);
    }

    public static HomePage getHomePage() {
        return homePage;
    }

    public boolean isTodoLabelVisible() {
        ExtentManager.log("STEP: Validating Todo Label at home screen");
        return checkForElementToBeVisible(propertyFileReader.getValue("todoLabel"), true);
    }

    public boolean isTextFieldHavingFocus() {
        ExtentManager.log("STEP: Validating if input field has focus by default once application launched");
        return isElementFocus(propertyFileReader.getValue("inputTextField"), true);
    }

    public void enterSingleTodoItem(String text) {
        enterText(propertyFileReader.getValue("inputTextField"), text, true);
        pressEnterKey();
        ExtentManager.log("STEP: User added \'" + text + "\' in the input field");
    }

    public void enterListOfTodoItems(Object items[]) {
        for (Object item : items) {
            enterSingleTodoItem((String) item);
        }
    }

    public boolean validateSequenceOfTodoItems(Object items[]) {
        List<WebElement> elements = getListOfElements(propertyFileReader.getValue("listOfTodoItems"),
                true);
        List<Object> todoText = getTextOfListOfElements(elements);
        for (int i = 0; i < items.length; i++) {
            if (!todoText.get(i).equals(items[i].toString().trim()))
                return false;
        }
        return true;
    }

    public boolean isItemAddedToTodoList(String text) {
        String locator = String.format(propertyFileReader.getValue("textOfTodoItems"), text);
        ExtentManager.log("STEP: Validated \'" + text + "\' added to todo list");
        return checkForElementToBeVisible(locator, true);

    }

    public boolean validateCountOfItemsLeft() {
        int count_of_items = getListOfElements(propertyFileReader.getValue("listOfTodoItems"), false)
                .size();
        String locator = String.format(propertyFileReader.getValue("countOfItemsLeft"), count_of_items);
        ExtentManager.log("STEP: Validated count of items added to todo list is correct");
        return checkForElementToBeVisible(locator, true);
    }

    public void removeElementFromTodoList(String itemToBeRemoved) {
        String listElement = String.format(propertyFileReader.getValue("textOfTodoItems"), itemToBeRemoved);
        String cancelButton = String.format(propertyFileReader.getValue("itemCancelButton"), itemToBeRemoved);
        mouseHoverAndClickOnElement(listElement, false);
        mouseHoverAndClickOnElement(cancelButton, false);
        ExtentManager.log("STEP: Removed item \'" + itemToBeRemoved + "\' from the todo list");
    }

    public boolean validateAbsenceOfItemFromList(String item) {
        String element = String.format(propertyFileReader.getValue("textOfTodoItems"), item);
        ExtentManager.log("STEP: Validated item \'" + item + "\' is removed from the todo list");
        return checkElementIsNotPresent(element, false);
    }

    public void modifyItemFromTodoList(String initialText, String modifiedText) {
        String listItem = String.format(propertyFileReader.getValue("textOfTodoItems"), initialText);
        mouseHoverAndDoubleClickOnElement(listItem, true);
        mouseHoverAndClickOnElement(propertyFileReader.getValue("editItemTextField"), true);
        editText(propertyFileReader.getValue("editItemTextField"), modifiedText, true);
        pressEnterKey();
        ExtentManager.log("STEP: Modified item \'" + initialText + "\' to" + modifiedText + " in the todo list");
    }

    public void markElementAsDone(String text) {
        String toggleButtonItem = String.format(propertyFileReader.getValue("itemToggleButton"), text);
        clickOnElement(toggleButtonItem, false);
        ExtentManager.log("STEP: Marked Todo item \'" + text + "\' as done");
    }

    public boolean validateItemIsMarkedAsCompleted(String text) {
        String listItem = String.format(propertyFileReader.getValue("completedItemStatusElement"), text);
        ExtentManager.log("STEP: Validating toggle button for \'" + text + "\' is selected");
        return getAttributeValue(listItem, "class", true).equalsIgnoreCase("completed") ? true : false;
    }

    public void clickOnClearCompleted() {
        clickOnElement(propertyFileReader.getValue("clearCompletedButton"), false);
        ExtentManager.log("STEP: Clicked on clear completed button");
    }

    public boolean verifySpacesRemovedFromListItem(String text) {
        ExtentManager.log("STEP: Validated surrounding spaces removed from todo item.");
        return isItemAddedToTodoList(text.trim());
    }

    public boolean checkVisibilityOfFooterElement() {
        return checkForElementToBeVisible(propertyFileReader.getValue("footerTextElement"), true);
    }

    public boolean checkVisibilityOfToggleButton(String text) {
        String toggleButtonItem = String.format(propertyFileReader.getValue("itemToggleButton"), text);
        return checkForElementToBeVisible(toggleButtonItem, false);
    }

    public boolean checkVisibilityOfCancelButton(String text) {
        String listElement = String.format(propertyFileReader.getValue("textOfTodoItems"), text);
        String cancelButton = String.format(propertyFileReader.getValue("itemCancelButton"), text);
        mouseHoverAndClickOnElement(listElement, false);
        return checkForElementToBeVisible(cancelButton, false);
    }
}
