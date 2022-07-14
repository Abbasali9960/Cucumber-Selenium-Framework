package Features;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
//mvn -Dhttps.protocols=TLSv1.2 install
@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/Features",
		glue= {"stepDefinition"},
	    plugin = {"pretty","html:target/cucumber-report/cucumber.html", "json:target/cucumber.json"},
		monochrome = true
		)
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
