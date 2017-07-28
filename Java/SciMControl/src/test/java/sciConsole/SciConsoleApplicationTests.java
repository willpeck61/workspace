package sciConsole;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","html:build/cucumber-html-report"},
features = "src/test/resources",    
glue = "sciConsole")

public class SciConsoleApplicationTests {
}
