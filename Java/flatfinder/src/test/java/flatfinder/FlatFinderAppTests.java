package flatfinder;

import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","html:build/cucumber-html-report"},
    features = "src/test/resources",
    glue = "flatfinder")

public class FlatFinderAppTests {

}
