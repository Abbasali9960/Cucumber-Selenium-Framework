package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.cucumber_framework.webconnector;


public class loginPage extends webconnector {
	
	@FindBy(name = "firstname")
	WebElement firstNameText;
	
	@FindBy(name = "lastname")
	WebElement lastNameText;

	@FindBy(id = "photo")
	WebElement photoButton;
	@FindBy(id = "continents")
	WebElement continent;


	// Initializing the Page Objects:
	public loginPage() {
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	public void setFirstName(String name){
        firstNameText.sendKeys(name);
          
	}
	public void setLastName(String name){
		lastNameText.sendKeys(name);
          
	}
	public void clickPhotoButton(){
		photoButton.click();
          
	}
	public WebElement getcontinent(){
		return continent;
          
	}
	public WebElement getPhotoButton(){
		return photoButton;
          
	}
}
