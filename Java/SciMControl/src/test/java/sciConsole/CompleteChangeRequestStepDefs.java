package sciConsole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sciConsole.controller.AccountController;
import sciConsole.controller.ChangeRequestController;
import sciConsole.controller.SessionController;
import sciConsole.domain.Request;
import sciConsole.domain.User;
import sciConsole.persistence.repository.RequestRepository;
import sciConsole.persistence.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {SciConsoleApplication.class,
		 SessionController.class,
		 AccountController.class,
		 ChangeRequestController.class,
		 SecurityConfig.class,
		 WebConfig.class})

public class CompleteChangeRequestStepDefs {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private ResultActions result;
	private Authentication authentication;
	
	@Autowired
	private UserRepository userRepo;
	private User usr;
	
	@Autowired
	private RequestRepository reqRepo;
	private Request req;

	private Request testreq;

	private Iterable<Request> reqSavePoint;

	private ModelAndView modview;

	private List<Request> reqlist;
	
	@Before
	public void setup() {
	    this.mockMvc = MockMvcBuilders
	            .webAppContextSetup(this.wac).build();
	    reqSavePoint = reqRepo.findAll();
	    testreq = new Request();
	    testreq.setVersionId(1);
	    testreq.setSciId(1);
	    testreq.setCreatedBy(1);
	    testreq.setStatus(1);
	    testreq.setReqDesc("Test Description");
	    testreq.setReqSolution("Test Solution");  
	    
	}
	
	@Given("^I am logged in as \"([^\"]*)\"$")
	public void i_am_logged_in_as(String arg1) throws Throwable {
	    usr = userRepo.findByLogin(arg1);
	    authentication = new UsernamePasswordAuthenticationToken(usr.getLogin(), usr.getPassword(), AuthorityUtils.createAuthorityList("ROLE_DEVELOPER"));
	    testreq.setAssignTo(usr.getId());
		testreq.setType(1);
		reqRepo.save(testreq);
		assertEquals(usr.getLogin(),arg1);
	    assertNotNull(usr);
	}

	@Given("^an approved change request \"([^\"]*)\"$")
	public void an_approved_change_request(String arg1) throws Throwable {
		Integer type = -1;
		Boolean test = false;
		if(arg1.equals("Fault")){
			type = 1;
		} else if (arg1.equals("New Feature")){
			type = 0;
		}
		assertNotNull(usr);
		result = this.mockMvc.perform(get("/assigned-requests"));
		result.andExpect(model().attributeExists("assigned"));
		reqlist = reqRepo.findByStatus(1);
		for(Request r : reqlist){
			if(r.getType() == 1){
				req = r;
				test = true;
			}
		}
		assertTrue(test);
	}

	@When("^I complete the task with data \"([^\"]*)\"$")
	public void i_complete_the_task_with_data(String arg1) throws Throwable {
		assertNotNull(req);
		result = this.mockMvc.perform(get("/complete-request?reqid="+req.getId()+"&complete="+arg1));
	}

	@Then("^the change request \"([^\"]*)\" is removed from my list$")
	public void the_change_request_is_removed_from_my_list(String arg1) throws Throwable {
		modview = result.andReturn().getModelAndView();
	    List<Request> assigned = (List<Request>) modview.getModelMap().get("assigned");
	    assertEquals(0, assigned.size());
	}

	@Then("^I should get the next approved request allocated to me$")
	public void i_should_get_the_next_approved_request_allocated_to_me() throws Throwable {
		result.andExpect(model().attributeExists("assigned"));
	}
	
	@After
	public void revertState(){
		reqRepo.deleteAll();
		reqRepo.save(reqSavePoint);
	}
}
