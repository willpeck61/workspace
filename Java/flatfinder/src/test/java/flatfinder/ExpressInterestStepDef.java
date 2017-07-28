package flatfinder;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.domain.PropertyInterest;
import flatfinder.persistence.repository.PropertyInterestRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.UserRepository;

public class ExpressInterestStepDef {
	
	@Autowired
	  private WebApplicationContext wac;
	  
	  @Autowired
	  private Filter springSecurityFilterChain;
	  
	  @Autowired
	  private UserRepository userRepo;
	  
	  @Autowired
	  private PropertyInterestRepository propInterestRepo;
	  
	  @Autowired
	  private PropertyRepository propRepo;
	  
	  private MockMvc mockMvc;
	  private ResultActions result;
	  private Authentication authentication;
	  
	  /**
	   * Set conditions for tests and save current repository states.
	   **/
	  @Before
	  public void setup() {
	    this.mockMvc = MockMvcBuilders
	        .webAppContextSetup(this.wac)
	        .addFilters(springSecurityFilterChain)
	        .apply(springSecurity())
	        .build();
	  }

	@Given("^im logged in as \"([^\"]*)\"$")
	public void im_logged_in_as(String arg1) throws Throwable {
		authentication = new UsernamePasswordAuthenticationToken(
		        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
	}

	@When("^the show interest button is clicked with the property id (\\d+)$")
	public void the_show_interest_button_is_clicked_with_the_property_id(Integer arg1) throws Throwable {
		result = mockMvc.perform(get("https://localhost/InterestedProperty")
		        .param("id",arg1.toString())
		        .param("username", authentication.getName())
		        .with(authentication(authentication)).with(csrf()));
	}
	
	@Then("^a notification is sent to the owner id (\\d+) with the property id (\\d+)$")
	public void a_notification_is_sent_to_the_owner_id_with_the_property_id(int arg1, int arg2) throws Throwable {
		List<PropertyInterest> allNotifs = propInterestRepo.findByUserIdAndPropertyId(arg1, arg2);
	  	boolean toCheck = false;
	  	  	
		for (int i = 0; i < allNotifs.size(); i++) {
			if(toCheck == false){
				if(allNotifs.get(i).getUserId() == arg1 && allNotifs.get(i).getPropertyId() == arg2){
					toCheck = true;
				}else{
					toCheck = false;
				}
			}
		}
		
		assertTrue(toCheck);	
	}

}
