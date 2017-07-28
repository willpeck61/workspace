package flatfinder;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet
.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet
.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet
.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet
.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet
.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet
.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication
.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.SecurityConfig;
import flatfinder.WebConfig;
import flatfinder.FlatFinder;
import flatfinder.controller.AccountController;
import flatfinder.controller.FeedbackPropertyController;
import flatfinder.controller.SessionController;
import flatfinder.domain.Property;
import flatfinder.domain.PropertyFeedback;
import flatfinder.domain.Report;
import flatfinder.domain.Role;
import flatfinder.domain.User;
import flatfinder.persistence.repository.PropertyFeedbackRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.ReportRepository;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserRepository;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
		SessionController.class,
		AccountController.class,
		SecurityConfig.class,
		WebConfig.class})

public class FeedbackStepDefs {

	private MockMvc mockMvc;
	private ResultActions result;
	private Authentication authentication;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private Filter springSecurityFilterChain;

	@Autowired
	private PropertyRepository propRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ReportRepository reportRepo;

	@Autowired
	private PropertyFeedbackRepository feedRepo;

	private Iterable<Role> roleSavePoint;
	private Iterable<User> userSavePoint;
	private Property property;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.addFilters(springSecurityFilterChain)
				.apply(springSecurity())
				.build();
		userSavePoint = userRepo.findAll();
		roleSavePoint = roleRepo.findAll();
	}

	@Given("^Im logged in as \"([^\"]*)\"$")
	public void im_logged_in_as(String arg1) throws Throwable {
		authentication = new UsernamePasswordAuthenticationToken(
				"alice", "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
	}

	@Given("^want to rate property \"([^\"]*)\"$")
	public void want_to_rate_property(String arg1) throws Throwable {
		property = propRepo.findById(Integer.parseInt(arg1));
		assertNotNull(property);
	}

	@When("^submitting the rating \"([^\"]*)\"$")
	public void submitting_the_rating(String arg1) throws Throwable {
		result = mockMvc.perform(post("https://localhost:8090/submitFeedback")        
				.param("id", property.getId().toString())
				.param("rating", arg1)
				.param("comment", "TEST")
				.with(authentication(authentication)).with(csrf()));
		result.andExpect(status().is(302));

	}

	@Then("^the rating is stored for property \"([^\"]*)\"$")
	public void the_rating_is_stored_for_property(String arg1) throws Throwable {
		List<PropertyFeedback> propfeed = feedRepo.findByPropertyId(property.getId());
		assertNotNull(propfeed);
	}

	@When("^the user \"([^\"]*)\" submits a report on \"([^\"]*)\" with subject \"([^\"]*)\" and description \"([^\"]*)\"$")
	public void the_user_submits_a_report_on_with_subject_and_description(String arg1, String arg2, String arg3, String arg4) throws Throwable {
		User user = userRepo.findByLogin(arg1);
		User repuser = userRepo.findByLogin(arg2);
		authentication = new UsernamePasswordAuthenticationToken(
				user.getLogin(), "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
		result = mockMvc.perform(post("https://localhost:8090/submitUserReport")        
				.param("username", repuser.getLogin())
				.param("subject", arg3)
				.param("description", arg4)
				.with(authentication(authentication)).with(csrf()));
		result.andExpect(status().is(302));
	}

	@Then("^the report on \"([^\"]*)\" exists made by \"([^\"]*)\"$")
	public void the_report_on_exists_made_by(String arg1, String arg2) throws Throwable {
		User usr = userRepo.findByLogin(arg2);
		User rep = userRepo.findByLogin(arg1);
		List<Report> report = reportRepo.findByReporterAndReportedUsername(usr.getLogin(), rep.getLogin());
		assertNotNull(report);
	}

}
