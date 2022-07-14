package stepDefinition;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.automation.cucumber_framework.webconnector;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.loginPage;
import io.cucumber.java.en.Then;

public class stepDef {
	        webconnector wb = new webconnector();
	        private String scenDesc;
        @Before
    	public void before(Scenario scenario) {
    		this.scenDesc = scenario.getName();
    		
    	}      
	   @Given("Website is launch")
		public void websiteLaunch() {
		     
			   
			   wb.setUpDriver();
		       wb.getURL(wb.driver);
		    
		}
		@When("When enter Fistname {string} and Last name {string}")
		public void enterName(String firstname,String secondname) {
		        
			     loginPage lp = new loginPage();
			     lp.setFirstName(firstname);
		         lp.setLastName(secondname);
		         
		   }
		@Then("Button is clicked")
		public void buttonClicker() {
		   
			loginPage lp = new loginPage();
			wb.scrollPage(lp.getcontinent(), wb.driver);
			 JavascriptExecutor executor = (JavascriptExecutor)wb.driver;
			    executor.executeScript("arguments[0].click();", lp.getPhotoButton());
			
			
		}
		 
	    @After
	    public void after(Scenario scenario){
	    	wb.closeDriver(scenario);
	    }
}


