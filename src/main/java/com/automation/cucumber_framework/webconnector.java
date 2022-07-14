package com.automation.cucumber_framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.FluentWait.*;
import org.openqa.selenium.support.ui.Wait;
import io.cucumber.core.api.*;
import io.cucumber.java.Scenario;

public class webconnector {

	public static WebDriver driver=null;
	public  SessionId session=null;
	public static Properties prop = new Properties();
	
	public webconnector(){
    	try {
    		prop.load( new FileInputStream("./src/main/java/config/config.properties") );
    	} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public WebDriver getDriver() {
		return this.getDriver();
	}
	
	public void setDriver(WebDriver driver){
		this.driver=driver;
	}

    public void getURL(WebDriver driver)
    {
        String Url = prop.getProperty("url");
        driver.get(Url);         
    }
    public void  setUpDriver(){
        String browser = prop.getProperty("browser");
        if (browser == null) {
            browser = "chrome";
        }
        switch (browser) {
            case "chrome":
            	System.setProperty("webdriver.chrome.driver","./src/test/java/lib/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
            	   //session = ((ChromeDriver)driver).getSessionId();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
            	System.setProperty("webdriver.gecko.driver","./src/test/lib/geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                 //session = ((FirefoxDriver)driver).getSessionId();
                break;
            default:
                throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
        }
      
    }
    
    public void closeDriver(Scenario scenario){
        if(scenario.isFailed()){
           saveScreenshotsForScenario(scenario);
        }
        driver.close();
    }

    private void saveScreenshotsForScenario(final Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png","snapshot");
    }
    
    public void waitForPageLoad(int timeout){
       ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
    }

    public String getSpecificColumnData(String FilePath, String SheetName, String ColumnName) throws InvalidFormatException, IOException {
    	FileInputStream fis = new FileInputStream(FilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(SheetName);
        XSSFRow row = sheet.getRow(0);
        int col_num = -1;
        for(int i=0; i < row.getLastCellNum(); i++)
        {
            if(row.getCell(i).getStringCellValue().trim().equals(ColumnName))
                col_num = i;
        }
        row = sheet.getRow(1);
        XSSFCell cell = row.getCell(col_num);
        String value = cell.getStringCellValue();
        fis.close();
        System.out.println("Value of the Excel Cell is - "+ value);    	 
    	return value;
    }
   
    
    public By getElementWithLocator(String WebElement) throws Exception {
		String locatorTypeAndValue = prop.getProperty(WebElement);
		String[] locatorTypeAndValueArray = locatorTypeAndValue.split(",");
		String locatorType = locatorTypeAndValueArray[0].trim();
		String locatorValue = locatorTypeAndValueArray[1].trim();
		switch (locatorType.toUpperCase()) {
		case "ID":
			return By.id(locatorValue);
		case "NAME":
			return By.name(locatorValue);
		case "TAGNAME":
			return By.tagName(locatorValue);
		case "LINKTEXT":
			return By.linkText(locatorValue);
		case "PARTIALLINKTEXT":
			return By.partialLinkText(locatorValue);
		case "XPATH":
			return By.xpath(locatorValue);
		case "CSS":
			return By.cssSelector(locatorValue);
		case "CLASSNAME":
			return By.className(locatorValue);
		default:
			return null;
		}
    }
    
    public WebElement FindAnElement(String WebElement) throws Exception{
    	return driver.findElement(getElementWithLocator(WebElement));
    }
    
    public void PerformActionOnElement(String WebElement, String Action, String Text) throws Exception {
    	switch (Action) {
		case "Click":
			FindAnElement(WebElement).click();
			break;
		case "Type":
			FindAnElement(WebElement).sendKeys(Text);
			break;
		case "Clear":
			FindAnElement(WebElement).clear();
			break;
		case "WaitForElementDisplay":
			waitForCondition("Presence",WebElement,60);
			break;
		case "WaitForElementClickable":
			waitForCondition("Clickable",WebElement,60);
			break;
		case "ElementNotDisplayed":
			waitForCondition("NotPresent",WebElement,60);
			break;
		default:
			throw new IllegalArgumentException("Action \"" + Action + "\" isn't supported.");
		}
    }
    
    public void waitForCondition(String TypeOfWait, String WebElement, int Time){
    	try {
	    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Time, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);
	    	switch (TypeOfWait)
	    	{
	    	case "PageLoad":
	    		wait.until( ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
	    		break;
	    	case "Clickable":
	    		wait.until(ExpectedConditions.elementToBeClickable(FindAnElement(WebElement)));
	    		break;
	    	case "Presence":
	    		wait.until(ExpectedConditions.presenceOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	case "Visibility":
	    		wait.until(ExpectedConditions.visibilityOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	case "NotPresent":
	    		wait.until(ExpectedConditions.invisibilityOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	default:
	    		Thread.sleep(Time*1000);
	    	}
    	}
	    	catch(Exception e)
	    	{
	    		throw new IllegalArgumentException("wait For Condition \"" + TypeOfWait + "\" isn't supported.");
	    	}
    }
    
    public void scrollPage(WebElement Element,WebDriver driver)
    {
    	 JavascriptExecutor js = (JavascriptExecutor) driver;
    	  js.executeScript("arguments[0].scrollIntoView();", Element);
    	
    }

}
