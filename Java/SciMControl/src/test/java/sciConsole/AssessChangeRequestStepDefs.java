package sciConsole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import sciConsole.persistence.repository.SciRepository;
import sciConsole.persistence.repository.UserRepository;
import sciConsole.persistence.repository.VersionRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {SciConsoleApplication.class,
		 SessionController.class,
		 AccountController.class,
		 ChangeRequestController.class,
		 SecurityConfig.class,
		 WebConfig.class})
public class AssessChangeRequestStepDefs {
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private ResultActions result;
	private Authentication authentication;
	
	@Autowired
	private UserRepository userRepo;
	private User user;
	
	@Autowired
	private SciRepository sciRepo;
	
	@Autowired
	private VersionRepository verRepo;
	
	@Autowired
	private RequestRepository reqRepo;
	private Request req;

	private Date parseDate;

	private String reqType;

	private Request testreq;

	private Iterable<Request> reqSavePoint;

	private ModelAndView modview;

	private Request request;
	
	@Before
	public void setup() {
	    this.mockMvc = MockMvcBuilders
	            .webAppContextSetup(this.wac).build();
	    reqSavePoint = reqRepo.findAll();
	    testreq = new Request();
	    testreq.setVersionId(1);
	    testreq.setSciId(1);
	    testreq.setCreatedBy(1);
	    testreq.setStatus(0);
	    testreq.setReqDesc("Test Description");
	    testreq.setReqSolution("Test Solution");
	    
	}
	
	@Given("^the pending change request \"([^\"]*)\" in a list of pending requests$")
	public void the_pending_change_request_in_a_list_of_pending_requests(String arg1) throws Throwable {
		Integer type = -1;
		Boolean test = false;
		if(arg1.equals("Fault")){
			type = 1;
		} else if (arg1.equals("New Feature")){
			type = 0;
		}
		testreq.setType(type);
		reqRepo.save(testreq);
		
		result = this.mockMvc.perform(get("/get-pending-requests"));
		result.andExpect(model().attributeExists("pending"));
		
		modview = result.andReturn().getModelAndView();
		List<Request> requests = (ArrayList<Request>) modview.getModelMap().get("pending");
		for(Request r : requests){
			if((r.getReqDesc()).equals("Test Description")){
				req = r;
			}
		}
		assertEquals(req.getReqDesc(),"Test Description");
	}

	@When("^I accept it with priority \"([^\"]*)\" for developer \"([^\"]*)\" with deadline \"([^\"]*)\"$")
	public void i_accept_it_with_priority_for_developer_with_deadline(String arg1, String arg2, String arg3) throws Throwable {
		user = userRepo.findByLogin(arg2);
		result = this.mockMvc.perform(get(
				"/update-pending-requests?reqid="+req.getId()+
				"&status=1"+
				"&priority="+arg1+
				"&assignto="+user.getId()+
				"&duedate="+arg3));
		
	}

	@Then("^the change request \"([^\"]*)\" is accepted$")
	public void the_change_request_is_accepted(String arg1) throws Throwable {
		Request r = reqRepo.findById(req.getId());
		assertEquals(r.getStatus(),1);
		reqType = arg1;
	}

	@Then("^removed from the list of pending requests$")
	public void removed_from_the_list_of_pending_requests() throws Throwable {
		Integer type = -1;
		Boolean test = true;
		
		if(reqType.equals("Fault")){
			type = 1;
		} else if (reqType.equals("New Feature")){
			type = 0;
		}

		List<Request> pending = reqRepo.findByStatus(0);
		for(Request r : pending){
			if (r.getId() == req.getId()){
				test = false;
			}
		}
		assertTrue(test);
	}

	@Then("^I should see the next pending request \\(if any\\)$")
	public void i_should_see_the_next_pending_request_if_any() throws Throwable {
		testreq = new Request();
	    testreq.setVersionId(1);
	    testreq.setSciId(1);
	    testreq.setCreatedBy(1);
	    testreq.setStatus(0);
	    testreq.setReqDesc("Test Description");
	    testreq.setReqSolution("Test Solution");
	    testreq.setType(0);
		reqRepo.save(testreq);
		
		testreq = new Request();
	    testreq.setVersionId(1);
	    testreq.setSciId(1);
	    testreq.setCreatedBy(0);
	    testreq.setStatus(0);
	    testreq.setReqDesc("Another Test Description");
	    testreq.setReqSolution("Another Test Solution");
	    testreq.setType(1);
		reqRepo.save(testreq);
		
		result = this.mockMvc.perform(get("/get-pending-requests"));
		result.andExpect(model().attributeExists("pending"));
	}

	@When("^I reject it with explanation \"([^\"]*)\"$")
	public void i_reject_it_with_explanation(String arg1) throws Throwable {
		modview = result.andReturn().getModelAndView();
		List<Request> requests = (ArrayList<Request>) modview.getModelMap().get("pending");
		for(Request r : requests){
			if((r.getReqDesc()).equals("Another Test Description")){
				req = r;
			}
		}
		result = this.mockMvc.perform(get(
				"/update-pending-requests?reqid="+req.getId()+
				"&status=4"+
				"&reason="+arg1));
	}

	@Then("^the change request \"([^\"]*)\" is rejected$")
	public void the_change_request_is_rejected(String arg1) throws Throwable {
		reqType = arg1;
	    List<Request> rejected = reqRepo.findByStatus(4);
	    Boolean isRejected = false;
	    for (Request r : rejected){
	    	if (r.getId() == req.getId()){
	    		isRejected = true;
	    	}
	    }
	    assertTrue(isRejected);
	}

	@Given("^I am assessing change requests$")
	public void i_am_assessing_change_requests() throws Throwable {
		result = this.mockMvc.perform(get("/get-pending-requests"));
	}

	@When("^I request to cancel the assessment$")
	public void i_request_to_cancel_the_assessment() throws Throwable {
		result.andExpect(model().attributeExists("pending"));
	}

	@Then("^I stop seeing change requests$")
	public void i_stop_seeing_change_requests() throws Throwable {
		result = this.mockMvc.perform(get("/success-login"));
		result.andExpect(model().attributeDoesNotExist("pending"));
	}
	@After
	public void revertState(){
		reqRepo.deleteAll();
		reqRepo.save(reqSavePoint);
	}
}
