## ToDoList App Automated Functional Tests ##

This is a maven project repository which contains the test scripts, page objects, and test data for testing 
functional flows of ToDoApp.
These scripts test functional workflows on popular web browsers such as Firefox and Chrome
Target URL is: https://todomvc.com/examples/angular2/

### Repo organization ###
This repository contains the following directories:

   ```
   |____src/main/java
   | |____base
   | |____constants
   | |____listeners
   | |____pages
   | |____reporting
   | |____utils
   |
   |____src/main/resources
   | |____elementRepository
   |
   |____src/test/java
   | |____testScripts
   |
   |____resources
   | |____testdata
   |
   |____reports
   ```

1. **testScripts** - Contains java test automation scripts to implement test steps for various test scenarios under 
todo application.
   Examples:
   ```
   |____testScripts
   | |____BaseTest.java
   | |____HomePageTest.java
   ```

2. **pages** - Tests are designed based on the Page Object Model (POM) design pattern. Under this model, each web page 
has a corresponding `page class` containing `page methods` which perform operations on page specific `WebElements`. 
Each web page under ToDo application has a dedicated page object `.java` file available under this directory. 
Every page class follows `Singleton` design pattern.
   Examples:
   ```
   |____pages
   | |____HomePage.java
   ```

3. **base** - Contains wrapper class implementing all the built-in selenium methods.
   Examples:
   ```
   |____base
   | |____PredefinedActions.java
   ```

4. **Other directories**
   ```
   | Directory         | Description                                                          |
   | :---------------- | :------------------------------------------------------------------  |
   | utils             | contains utility methods for test automation                         |
   | reporting         | contains extent reporting configuration                              |
   | constants         | contains data which is contant like filepaths, locator strategies    |
   | listeners         | contains listeners for test automation which listens different events|
   | elementRepository | contains property files containing locators of each page             |
   | testdata          | contains excel sheets with testdata                                  |
   ```

### Getting Started ###

Following are the instructions to set up a development environment to develop, execute and maintain automated 
functional tests for TodoApp.

### Libraries Used in Framework ###

Following are the list of libraries used while building the framework:

   ```
   | Library Name      | Version  | Description                                                                                                 |
   | :---------------- | :------- | :-----------------------------------------------------------------------------------------------------------|
   | testng            | 7.4.0    | Testing framework which supports test configured by annotations, data-driven testing, parametric tests, etc.|
   | selenium          | 3.141.59 | It is a browser automation tool.                                                                            |
   | extentreports     | 5.0.6    | Used for creation of interactive reports for every execution                                                |
   | javafaker         | 1.0.2    | Library that generates random data                                                                          |
   | apache poi        | 4.1.0    | Java API To Access Microsoft Format Files, used for reading excel sheet data                                |
   | webdrivermanager  | 5.2.0    | Automated driver management and other helper features for Selenium WebDriver in Java                        |
   ```

### Prerequisites ###

1. Download, install or update to the latest version of Web browsers - Chrome, Firefox, and Safari

2. Install IDE like IntelliJ IDEA/ Eclipse.

3. Install git on the system to perform git commands.

4. Download and setup java v17.0.2. Follow instructions on:

### Environment setup ###

1. Clone the code from `ToDoAppUIAutomation` repo
    - `HTTPS: https://github.com/VarshaTi97/ToDoAppUIAutomation.git`

2. Install dependencies
    - Install required dependencies.
      ```
      cd ToDoAppUIAutomation/
      mvn clean compile
      ```

### Setup for execution on selenium-grid in docker environment ###

1. Install docker desktop on system.

2. Execute following command to start selenium-grid containers:
   ```
   cd ToDoAppUIAutomation/
   docker-compose -f docker-compose.yml up -d
   ```

### Usage / Running Tests ###
Open the terminal and execute commands using options like:
   ```
   | Browser   | Platform  | Command                                                              |
   | :-------- | :-------- | :------------------------------------------------------------------  |
   | Chrome    |  local    |  mvn clean test -DbrowserName=chrome -DexecutionType=local           |
   | FireFox   |  local    |  mvn clean test -DbrowserName=firefox -DexecutionType=local          |
   | Chrome    |  grid     |  mvn clean test -DbrowserName=chrome -DexecutionType=grid            |
   | FireFox   |  grid     |  mvn clean test -DbrowserName=firefox -DexecutionType=grid            |
   ```
### Execution Report ###

- Execution report can be found inside the `reports` folder. Report files are `.html` having execution time and date in 
filename.
- Report files can be opened in any web browser.

### Author ###

- Varsha Tiwari
