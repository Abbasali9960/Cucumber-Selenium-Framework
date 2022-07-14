package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class stepDef {

		@Given("User is on Home Page")
		public void user_is_on_home_page() {
		     
			   System.out.println("BaBA CHAL GAYA");
		    
		}
		@When("User Navigate to LogIn Page")
		public void user_navigate_to_log_in_page() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new io.cucumber.java.PendingException();
		}
		@When("User enters UserName and Password")
		public void user_enters_user_name_and_password() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new io.cucumber.java.PendingException();
		}
		@Then("Message displayed Login Successfully")
		public void message_displayed_login_successfully() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new io.cucumber.java.PendingException();
		}
}


