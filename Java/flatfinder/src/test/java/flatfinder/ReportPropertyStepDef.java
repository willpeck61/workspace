package flatfinder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.domain.Property;
import flatfinder.domain.Report;
import flatfinder.domain.User;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.ReportRepository;
import flatfinder.persistence.repository.UserRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ReportPropertyStepDef {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private Filter springSecurityFilterChain;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ReportRepository reportRepo;
	
	@Autowired
	PropertyRepository propertyRepo;

	private MockMvc mockMvc;
	private ResultActions result;
	private Authentication authentication;
	private User user;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.addFilters(springSecurityFilterChain)
				.apply(springSecurity())
				.build();
		try {
			result = mockMvc.perform(get("https://localhost/user-login"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@When("^the user \"([^\"]*)\" submits report on property with id \"([^\"]*)\" and subject \"([^\"]*)\" and description \"([^\"]*)\"$")
	public void the_user_submits_report_on_property_with_id_and_subject_and_type(String arg1, int arg2, String arg3, String arg4) throws Throwable {
		user = new User();
		user.setFirstName(arg1);
		
		result = mockMvc.perform(post("https://localhost:8090/submitPropertyReport")
				.param("propId", Integer.toString(arg2))
				.param("subject", arg3)
				.param("description", arg4)
				.with(csrf())
				.with(authentication(authentication)));
		result.andExpect(status().is(302));
		
		Report report = new Report();
		report.setId(arg2);
		report.setReportsub(arg3);
		report.setReportType(arg4);
		report.setReporter(user.getFirstName());
		reportRepo.save(report);
	}

	@Then("^the report on property \"([^\"]*)\" exists made by \"([^\"]*)\"$")
	public void the_report_on_property_exists_made_by(String arg1, String arg2) throws Throwable {
		Report report = reportRepo.findById(Integer.parseInt(arg1));
		assertNotNull(report.getId());
		assertNotNull(report.getReportsub());
		assertNotNull(report.getReportType());
		assertEquals(report.getReporter(), arg2);
	}
}







