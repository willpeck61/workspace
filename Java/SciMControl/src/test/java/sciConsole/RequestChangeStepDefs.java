package sciConsole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sciConsole.SciConsoleApplication;
import sciConsole.SecurityConfig;
import sciConsole.WebConfig;
import sciConsole.controller.AccountController;
import sciConsole.controller.ChangeRequestController;
import sciConsole.controller.SessionController;
import sciConsole.domain.Request;
import sciConsole.domain.Sci;
import sciConsole.domain.Ver;
import sciConsole.persistence.repository.RequestRepository;
import sciConsole.persistence.repository.SciRepository;
import sciConsole.persistence.repository.VersionRepository;


@WebAppConfiguration
@ContextConfiguration(classes = {SciConsoleApplication.class,
		 SessionController.class,
		 AccountController.class,
		 ChangeRequestController.class,
		 SecurityConfig.class,
		 WebConfig.class})
public class RequestChangeStepDefs {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private ResultActions result;
	
	@Autowired
	private SciRepository sciRepo;
	
	@Autowired
	private VersionRepository verRepo;
	
	private Ver version;
	private Sci sci;
	@Autowired
	private RequestRepository reqRepo;
	private Request request;
	
	private String description;
	private String solution;
	private ModelAndView modview;

	private Integer sciid;

	private UsernamePasswordAuthenticationToken authentication;
	
	@Before
	public void setup() {
	    this.mockMvc = MockMvcBuilders
	            .webAppContextSetup(this.wac).build(); 
	}
	
	@Given("^a SCI \"([^\"]*)\"$")
	public void a_SCI(String arg1) throws Throwable {
		sci = sciRepo.findByName(arg1);
		sciid = sci.getId();
		assertNotNull(sci);
	}

	@Given("^a head version \"([^\"]*)\"$")
	public void a_head_version(String arg1) throws Throwable {
	    version = verRepo.findByName(arg1);
	    assertNotNull(version);
	}

	@Given("^a problem description \"([^\"]*)\"$")
	public void a_problem_description(String arg1) throws Throwable {
		description = arg1;
	}

	@Given("^a proposed solution \"([^\"]*)\"$")
	public void a_proposed_solution(String arg1) throws Throwable {
	    solution = arg1;
	}

	@When("^I request a change request for reporting a fault$")
	public void i_request_a_change_request_for_reporting_a_fault() throws Throwable {
		authentication = new UsernamePasswordAuthenticationToken("Bob", "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		result = this.mockMvc.perform(get(
				"/newChange?sci="+sci.getName()+
				"&version="+version.getName()+
				"&description="+description+
				"&solution="+solution+
				"&createdby=1&type=0"));
	}

	@Then("^the system stores the fault request \"([^\"]*)\" for the head version \"([^\"]*)\" of \"([^\"]*)\" with solution \"([^\"]*)\"$")
	public void the_system_stores_the_fault_request_for_the_head_version_of_with_solution(String arg1, String arg2, String arg3, String arg4) throws Throwable {
		result.andExpect(model().attributeExists("req"));
		modview = result.andReturn().getModelAndView();
		Request req = (Request) modview.getModelMap().get("req");
		Integer reqid = req.getId();
		request = reqRepo.findById(reqid);
		assertEquals(request.getReqDesc(),description);
	}
	
	@When("^shows a summary of the change request$")
	public void shows_a_summary_of_the_change_request() throws Throwable {
		modview = result.andReturn().getModelAndView();
		assertEquals("new-change-request",modview.getViewName());
	}

	@When("^I request a new feature$")
	public void i_request_a_new_feature() throws Throwable {
		authentication = new UsernamePasswordAuthenticationToken("Bob", "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		result = this.mockMvc.perform(get(
				"/newChange?sci="+sci.getName()+
				"&version="+version.getName()+
				"&description="+description+
				"&solution="+solution+
				"&createdby=1&type=1"));
	}

	@Then("^the system stores the new feature request \"([^\"]*)\" for the head version \"([^\"]*)\" of \"([^\"]*)\" with solution \"([^\"]*)\"$")
	public void the_system_stores_the_new_feature_request_for_the_head_version_of_with_solution(String arg1, String arg2, String arg3, String arg4) throws Throwable {
		result.andExpect(model().attributeExists("req"));
		modview = result.andReturn().getModelAndView();
		Request req = (Request) modview.getModelMap().get("req");
		Integer reqid = req.getId();
		request = reqRepo.findById(reqid);
		assertEquals(request.getReqDesc(),description);
	}

	@Then("^The system shows a summary of the change request$")
	public void the_system_shows_a_summary_of_the_change_request() throws Throwable {
		modview = result.andReturn().getModelAndView();
		assertEquals("new-change-request",modview.getViewName());
	}
}
