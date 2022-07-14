package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
//mvn -Dhttps.protocols=TLSv1.2 install
@RunWith(Cucumber.class)
@CucumberOptions(
		features = "E:\\eclipse-workspace\\cucumber-framework\\src\\test\\java\\Features",
		glue= {"stepDefinition"},
	    plugin = {"pretty","html:target/cucumber-report/cucumber.html", "json:target/cucumber.json"},
		monochrome = true
		)
public class runCuke {

}
