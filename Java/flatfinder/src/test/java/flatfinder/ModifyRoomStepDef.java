package flatfinder;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.controller.AccountController;
import flatfinder.controller.SessionController;
import flatfinder.domain.Property;
import flatfinder.domain.Room;
import flatfinder.domain.User;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.RoomRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
    SessionController.class,
    AccountController.class,
    SecurityConfig.class,
    WebConfig.class,
})

public class ModifyRoomStepDef{
	
	@Autowired
	  private WebApplicationContext wac;
	  
	  @Autowired
	  private Filter springSecurityFilterChain;
	  
	  @Autowired
	  private PropertyRepository propRepo;

	  @Autowired
	  private RoomRepository roomRepo;
	  
	  private MockMvc mockMvc;
	  private ResultActions result;
	  private Authentication authentication;
	  public User currentUser;
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
	
	@Then("^the rooms associated with the property id \"([^\"]*)\" should be displayed$")
	public void the_rooms_associated_with_the_property_id_should_be_displayed(String arg1) throws Throwable {
		result = mockMvc.perform(get("/landlord-dashboard/manage/editRoom?id="+arg1));
		result.andExpect(redirectedUrl("https://localhost/landlord-dashboard/manage/editRoom?id="+arg1));
		testUrl = "/landlord-dashboard/manage/editRoom?id="+arg1;
	}

	@Given("^im the user \"([^\"]*)\" completing the form for property id \"([^\"]*)\"$")
	public void im_the_user_completing_the_form_for_property_id(String arg1, String arg2) throws Throwable {
		authentication = new UsernamePasswordAuthenticationToken(
		        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_LANDLORD"));
		result = mockMvc.perform(get("/landlord-dashboard/manage/editRoom?id="+arg2));
		result.andExpect(redirectedUrl("https://localhost/landlord-dashboard/manage/editRoom?id="+arg2));
		testUrl = "/landlord-dashboard/manage/editRoom?id="+arg2;
	}

	@When("^the create room form is submitted with (\\d+), \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)$")
	public void the_create_room_form_is_submitted_with_and(Integer propertyID, String headLine, String desc, Integer width, Integer height, Integer furnished, Integer ensuite, Integer doubleRoom) throws Throwable {
		
		Boolean f = (furnished != 0);
		Boolean e = (ensuite != 0);
		Boolean dr = (doubleRoom != 0);
		
		MockMvcBuilders.webAppContextSetup(this.wac).build()
	      .perform(post("https://localhost:8090/submitAddedRoomRWOI")
	    		  	.param("propertyid", propertyID.toString())
	    		  	.param("furnished", f.toString())
	    		  	.param("headline", headLine)
	    		  	.param("description", desc)
	    		  	.param("ensuite", e.toString())
	    		  	.param("doubleRoom", dr.toString())
	    		  	.param("width", width.toString())
	    		  	.param("height", height.toString())
	    		  	.param("location", "landlord-dashboard/manage")
	          .with(csrf())
	          .with(authentication(authentication)));  
	}

	@Then("^we should see  (\\d+), \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+) has been created\\.$")
	public void we_should_see_and_has_been_created(Integer propertyID, String headLine, String desc, Integer width, Integer height, Integer furnished, Integer ensuite, Integer doubleRoom) throws Throwable {
		List<Room> allrooms = (List<Room>) roomRepo.findByPropertyId(propertyID);
	    
	    boolean toReturn = true;
	    boolean toCheck = false;
		
		Boolean f = (furnished != 0);
		Boolean e = (ensuite != 0);
		Boolean dr = (doubleRoom != 0);
		
		for(int i = 0; i < allrooms.size(); i++){
			if(toCheck == false){
				Room addedRoom = roomRepo.findById(allrooms.get(i).getId());
				
				if(addedRoom.getFurnished() == f){
					if(addedRoom.getHeadline().equals(headLine)){
						if(addedRoom.getDescription().equals(desc)){
							if(addedRoom.getEnsuite() == e){
								if(addedRoom.getDoubleRoom() == dr){
									if(addedRoom.getWidth() == width){
										if(addedRoom.getHeight() == height){
											toCheck = true;
										}
									}
								}
							}
						}
					}
				}	
			}
		}
		
		assertEquals(toReturn,toCheck);
	}
	

	@When("^the save form is submitted with (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)$")
	public void the_save_form_is_submitted_with_and(Integer roomID, Integer propertyID, String headLine, String desc, Integer width, Integer height, Integer furnished, Integer ensuite, Integer doubleRoom) throws Throwable {
		
		Boolean f = (furnished != 0);
		Boolean e = (ensuite != 0);
		Boolean dr = (doubleRoom != 0);
		
		MockMvcBuilders.webAppContextSetup(this.wac).build()
	      .perform(post("https://localhost:8090/submitUpdateRoomRWOI")
	    		  	.param("propertyid", propertyID.toString())
	    		  	.param("roomid", roomID.toString())
	    		  	.param("furnished", f.toString())
	    		  	.param("headline", headLine)
	    		  	.param("description", desc)
	    		  	.param("ensuite", e.toString())
	    		  	.param("doubleRoom", dr.toString())
	    		  	.param("width", width.toString())
	    		  	.param("height", height.toString())
	    		  	.param("location", "landlord-dashboard/manage")
	          .with(csrf())
	          .with(authentication(authentication)));  
	}

	@Then("^we should see (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+) has been updated$")
	public void we_should_see_and_has_been_updated(Integer roomID, Integer propertyID, String headLine, String desc, Integer width, Integer height, Integer furnished, Integer ensuite, Integer doubleRoom) throws Throwable {
		
		boolean toReturn = true;
	    boolean toCheck = false;
		
		Boolean f = (furnished != 0);
		Boolean e = (ensuite != 0);
		Boolean dr = (doubleRoom != 0);
		
		Room currentRoom = roomRepo.findById(roomID);
		
		if(currentRoom.getFurnished() == f){
			if(currentRoom.getHeadline().equals(headLine)){
				if(currentRoom.getDescription().equals(desc)){
					if(currentRoom.getEnsuite() == e){
						if(currentRoom.getDoubleRoom() == dr){
							if(currentRoom.getWidth() == width){
								if(currentRoom.getHeight() == height){
									toCheck = true;
								}
							}
						}
					}
				}
			}
		}
		assertEquals(toReturn,toCheck);
	}
}