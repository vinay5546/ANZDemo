package com.mypackage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinition {
    WebDriver driver = null;

    // Expected Texts
    String URL = "https://www.anz.com.au/personal/home-loans/calculators-tools/much-borrow/";
    String expectedMessage = "Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions, call us on 1800 035 500.";

    // All locators Bys
    By applicationTypeSingleBy = By.id("application_type_single");
    By applicationTypeJointBy = By.id("application_type_joint");
    By noOfDependentsBy = By.xpath("//select[@title='Number of dependants']");
    By homeToLiveInBy = By.id("borrow_type_home");
    By residentialInvestmentBy = By.id("borrow_type_investment");
    By incomeBy = By.xpath("//input[@aria-labelledby='q2q1']");
    By otherIncomeBy = By.xpath("//input[@aria-labelledby='q2q2']");
    By livingExpensesBy = By.id("expenses");
    By currentHomeLoanRepaymentsBy = By.id("homeloans");
    By otherLoanRepaymentsBy = By.id("otherloans");
    By otherCommitmentsBy = By.xpath("//input[@aria-labelledby='q3q4']");
    By totalCreditCardLimitsBy = By.id("credit");
    By btnBorrowBy = By.id("btnBorrowCalculater");
    By borrowResultBy = By.id("borrowResultTextAmount");
    By startOverButtonBy = By.xpath("//div[@class='borrow__result text--white clearfix']//button");
    By errorMessageBy = By.cssSelector(".borrow__error__text");

    @Before
    public void setUp(){
        // Setting system properties of ChromeDriver
        //System.setProperty("webdriver.chrome.driver", "C://Selenium-java browserstack//chromedriver_win32//chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Browser Opened");

        driver.manage().window().maximize();
        System.out.println("Browser Maximized");
    }

    @Given("^I launch the application$")
    public void launchApplication() {

        driver.navigate().to(URL);
        System.out.println("Navigated to : "+URL);
    }

    @Then("^I am on '(.*)' page$")
    public void verifyANZHomeLoanCalculatorPage(String expectedTitle) {
        Assert.assertEquals(driver.getTitle(), expectedTitle);
        System.out.println("Page title verified");
    }

    @When("^I select 'Application type' as '(.*)'$")
    public void selectApplicationType(String applicationType) throws Exception {
        if(applicationType.equals("Single")) {
            WebElement single = driver.findElement(applicationTypeSingleBy);
            single.click();
        } else if(applicationType.equals("Joint")){
            WebElement joint = driver.findElement(applicationTypeJointBy);
            joint.click();
        } else {
            throw new Exception("Invalid Application type : "+applicationType);
        }
        System.out.println("Application type selected as : "+applicationType);
    }

    @When("^I select 'Number of dependents' as '(.*)'$")
    public void selectNumberOfDependents(String noOfDependents) throws Exception {
        Select dep =  new Select(driver.findElement(noOfDependentsBy));
        dep.selectByVisibleText(noOfDependents);
        System.out.println("Number of dependents selected as : "+noOfDependents);
    }

    @When("^I select 'Property i would like to buy' as '(.*)'$")
    public void selectPropertyYouWouldLikeToBuy(String propertyType) throws Exception {
        if(propertyType.equals("Home to live in")) {
            WebElement homeToLive = driver.findElement(homeToLiveInBy);
            homeToLive.click();
        } else if(propertyType.equals("Residential investment")){
            WebElement residentialInvest = driver.findElement(residentialInvestmentBy);
            residentialInvest.click();
        } else {
            throw new Exception("Invalid 'Home to live in' option : "+propertyType);
        }
        System.out.println("'Home to live in' selected as : "+propertyType);
    }

    @When("^I enter '(Income|Other income|Living expenses|Current home loan repayments|Other loan repayments|Other commitments|Total credit card limits)' as '(.*)'$")
    public void enterEarningsAndExpenses(String field, String value) throws Exception {

        By textField;
        switch (field) {
            case "Income" -> textField = incomeBy;
            case "Other income" -> textField = otherIncomeBy;
            case "Living expenses" -> textField = livingExpensesBy;
            case "Current home loan repayments" -> textField = currentHomeLoanRepaymentsBy;
            case "Other loan repayments" -> textField = otherLoanRepaymentsBy;
            case "Other commitments" -> textField = otherCommitmentsBy;
            case "Total credit card limits" -> textField = totalCreditCardLimitsBy;
            default -> throw new Exception("Un-expected value:" + field);
        }

        WebElement fieldElement = driver.findElement(textField);
        fieldElement.clear();
        fieldElement.sendKeys(value);
        System.out.println("Value: "+value+" entered in the "+field+" field");
    }

    @When("^I click on 'Work out how much i could borrow' button$")
    public void clickBorrowButton() {
        WebElement borrowButton = driver.findElement(btnBorrowBy);
        borrowButton.click();
        System.out.println("Borrow button clicked");
    }

    @Then("^I verify the borrowing estimate as '(.*)'$")
    public void verifyBorrowingEstimate(String expectedEstimate) {
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(borrowResultBy), expectedEstimate));
        Assert.assertEquals(driver.findElement(borrowResultBy).getText(), expectedEstimate);
        System.out.println("Borrowing estimate verified as: "+expectedEstimate);
    }

    @Then("^I verify the error message$")
    public void verifyErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(errorMessageBy), expectedMessage));
        Assert.assertEquals(driver.findElement(errorMessageBy).getText(), expectedMessage);
        System.out.println("Error message verified");
    }

    @When("^I click 'Start over' button$")
    public void clickStartOverButton() {
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(ExpectedConditions.elementToBeClickable(startOverButtonBy));
        driver.findElement(startOverButtonBy).click();
        System.out.println("Clicked Start over button");
    }

    @When("^I validate form gets cleared$")
    public void validateFormCleared() {

        // Verify button disappeared
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(startOverButtonBy));
        System.out.println("Start over button disappeared");

        // Verify all fields set to default
        Assert.assertNotNull(driver.findElement(applicationTypeSingleBy).getAttribute("checked"));
        Assert.assertNotNull(driver.findElement(homeToLiveInBy).getAttribute("checked"));
        Select dep =  new Select(driver.findElement(noOfDependentsBy));
        Assert.assertEquals(dep.getFirstSelectedOption().getText(),"0");
        Assert.assertEquals(driver.findElement(incomeBy).getText(),"");
        Assert.assertEquals(driver.findElement(otherIncomeBy).getText(),"");
        Assert.assertEquals(driver.findElement(livingExpensesBy).getText(),"");
        Assert.assertEquals(driver.findElement(currentHomeLoanRepaymentsBy).getText(),"");
        Assert.assertEquals(driver.findElement(otherLoanRepaymentsBy).getText(),"");
        Assert.assertEquals(driver.findElement(totalCreditCardLimitsBy).getText(),"");
        System.out.println("All field values set to default");
    }

    @After
    public void cleanUp(){
        driver.close();
    }
}
