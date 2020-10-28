package BDDIOTestNG;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = {"BDDIOTestNG"})
public class RunParallel extends AbstractTestNGCucumberTests {

	/*@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}*/
	
	@RunWith(Cucumber.class)
	@CucumberOptions(plugin = {"pretty"})
	public class RunCucumberTest {

	}

}