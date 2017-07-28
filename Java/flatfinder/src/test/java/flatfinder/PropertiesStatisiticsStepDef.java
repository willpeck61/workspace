package flatfinder;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.Filter;

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

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.controller.AccountController;
import flatfinder.controller.SessionController;
import flatfinder.domain.Notification;
import flatfinder.domain.Property;
import flatfinder.domain.User;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
    SessionController.class,
    AccountController.class,
    SecurityConfig.class,
    WebConfig.class})

public class PropertiesStatisiticsStepDef {
  
  @Autowired
  private WebApplicationContext wac;
  
  @Autowired
  private Filter springSecurityFilterChain;
  
  @Autowired
  private PropertyRepository propRepo;
  
  @Autowired
  private NotificationRepository notiRepo;

  
  private MockMvc mockMvc;
  private ResultActions result;
  private Authentication authentication;
  
  private Property currentProperty;
  private Integer numOfViews;
  
  private String testUrl;
  
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
	
	@When("^the user \"([^\"]*)\" visits a property with id \"([^\"]*)\"$")
	public void the_user_visits_a_property_with_id(String userLogin, String propertyId) throws Throwable {
		
		authentication = new UsernamePasswordAuthenticationToken(
		        "Alice", "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
		
		int propIdInt = Integer.parseInt(propertyId);
	    currentProperty = propRepo.findById(propIdInt);
	    numOfViews = currentProperty.getNumViews();
	    
	    result = mockMvc.perform(get("https://localhost/property")
		        .param("id",propertyId)
		        .with(authentication(authentication)).with(csrf()));
	}
	
	@Then("^the number of views on property with id \"([^\"]*)\" should increase by one$")
	public void the_number_of_views_on_property_with_id_should_increase_by_one(String propertyId) throws Throwable {
		boolean toReturn = true;
	    boolean toCheck = false;
	    
	    int propIdInt = Integer.parseInt(propertyId);
		
	    Property currentProperty1 = propRepo.findById(propIdInt);
	    
		Integer newViews = currentProperty1.getNumViews();
		
		if(newViews > numOfViews){
			toCheck = true;
		}else{
			toCheck = false;
		}
		
		assertEquals(toReturn,toCheck);
	}
	
	@When("^the user \"([^\"]*)\" express' interest in a property with id \"([^\"]*)\"$")
	public void the_user_express_interest_in_a_property_with_id(String user, String propertyId) throws Throwable {
		result = mockMvc.perform(get("https://localhost/InterestedProperty?id=" + propertyId)
    			.with(authentication(authentication))
    			.with(csrf()))
    			.andDo(print());
	}
	
	@Then("^\"([^\"]*)\" should be send a notification from \"([^\"]*)\"$")
	public void should_be_send_a_notification_from(String landlord, String user) throws Throwable {
		boolean toReturn = true;
	    boolean toCheck = false;
		
		List<Notification> noti = notiRepo.findByForUserAndCreatedBy(landlord, user);
		
		for(int i = 0; i < noti.size(); i++){
			if(noti.get(i).getCreatedBy().equals(user)){
				toCheck = true;
			}
		}
		
		assertEquals(toReturn,toCheck);
	}
}