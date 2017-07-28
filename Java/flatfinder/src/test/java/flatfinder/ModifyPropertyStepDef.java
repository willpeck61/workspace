package flatfinder;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.controller.AccountController;
import flatfinder.controller.SessionController;
import flatfinder.domain.Property;
import flatfinder.domain.User;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
    SessionController.class,
    AccountController.class,
    SecurityConfig.class,
    WebConfig.class,
})

public class ModifyPropertyStepDef{
	
	  @Autowired
	  private WebApplicationContext wac;
	  
	  @Autowired
	  private Filter springSecurityFilterChain;
	  
	  @Autowired
	  private PropertyRepository propRepo;
	  
	  @Autowired
	  private UserRepository userRepo;
	  
	  private MockMvc mockMvc;
	  private ResultActions result;
	  private Authentication authentication;
	  public User currentUser;
	  private Property oldProperty; 
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
	  
	  @Given("^im the user \"([^\"]*)\" on the landlord dashboard$")
		public void im_the_user_on_the_landlord_dashboard(String arg1) throws Throwable {
			authentication = new UsernamePasswordAuthenticationToken(
			        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_LANDLORD"));
		}
	  
	   @Given("^im the user \"([^\"]*)\" completing the \"([^\"]*)\" form$")
	   public void im_the_user_completing_the_form(String arg1, String arg2) throws Throwable {
		   authentication = new UsernamePasswordAuthenticationToken(
			        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_LANDLORD"));
		   currentUser = userRepo.findByLogin(arg1);
		  
		   	result = mockMvc.perform(get("/landlord-dashboard/create"));
			result.andExpect(redirectedUrl("https://localhost/landlord-dashboard/create"));
			testUrl = "/landlord-dashboard/create";
	   }


		@Then("^the form to create a new property is displayed$")
		public void the_form_to_create_a_new_property_is_displayed() throws Throwable {
			 result = mockMvc.perform(get("/landlord-dashboard/create"));
			 result.andExpect(redirectedUrl("https://localhost/landlord-dashboard/create"));
			 testUrl = "/landlord-dashboard/create";
		}

		@When("^the create property form is submitted with (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)$")
		public void the_create_property_form_is_submitted_with_and(Integer price, Integer houseNum, String address1, String address2, String city, String postcode, Integer fromDD, Integer fromMM, Integer fromYYYY, Integer toDD, Integer toMM, Integer toYYYY, Integer propertyType, String advertTitle, String desc, String localArea, Integer No_Of_Rooms, String occupantType, Integer billsIncluded, Integer petsAllowed, Integer parking, Integer disabledAccess, Integer smoking, Integer quietArea, Integer shortTerm, Integer singleGender) throws Throwable {			
			MockMvcBuilders.webAppContextSetup(this.wac).build()
		      .perform(post("https://localhost:8090/submitAddedPropertyPWOI")
		    		  	.param("price", price.toString())
						.param("houseNum", houseNum.toString())   
				        .param("address1", address1)
				        .param("address2", address2)
				        .param("address3", city)
				        .param("postcode", postcode)
				        
				        .param("fromDD", fromDD.toString())
				        .param("fromMM", fromMM.toString()) 
				        .param("fromYYYY", fromYYYY.toString())
				        .param("toDD", toDD.toString())
				        .param("toMM", toMM.toString())
				        .param("toYYYY", toYYYY.toString())
				        .param("propertytype", propertyType.toString())
				        .param("advertTitle", advertTitle)
				        .param("description", desc)
				        .param("localArea", localArea)				        
				        
				        .param("occupantType", occupantType)
				        .param("billsincluded", billsIncluded.toString())
				        .param("petsallowed", petsAllowed.toString())
				        .param("parking", parking.toString())
				        .param("disabledaccess", disabledAccess.toString())
				        .param("smoking", smoking.toString())
				        .param("quietarea", quietArea.toString())
				        .param("shortterm", shortTerm.toString())
				        .param("singlegender", singleGender.toString())
				        .param("currentName", currentUser.getLogin())
				        .param("currentId", currentUser.getId().toString())
		          .with(csrf())
		          .with(authentication(authentication)));  
		}

		@Then("^we should see (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)  has been created$")
		public void we_should_see_and_has_been_created(Integer price, Integer houseNum, String address1, String address2, String city, String postcode, Integer fromDD, Integer fromMM, Integer fromYYYY, Integer toDD, Integer toMM, Integer toYYYY, Integer propertyType, String advertTitle, String desc, String localArea, Integer No_Of_Rooms, String occupantType, Integer billsIncluded, Integer petsAllowed, Integer parking, Integer disabledAccess, Integer smoking, Integer quietArea, Integer shortTerm, Integer singleGender) throws Throwable {
	    List<Property> allProperties = (List<Property>) propRepo.findAll();
	    
	    boolean toReturn = true;
	    boolean toCheck = false;
	    
	    Boolean bi = (billsIncluded != 0);
		Boolean pa = (petsAllowed != 0);
		Boolean p = (parking != 0);
		Boolean da = (disabledAccess != 0);
		Boolean s = (smoking != 0);
		Boolean qa = (quietArea != 0);
		Boolean st = (shortTerm != 0);
		Boolean sg = (singleGender != 0);
		
		
		for(int i = 0; i < allProperties.size(); i++){
			if(toCheck == false){
				Property updatedProp = propRepo.findById(allProperties.get(i).getId());
			
				if(updatedProp.getPricePerWeek() == price){
					if(updatedProp.getHouseNum().equals(houseNum.toString())){
						if(updatedProp.getAddress1().equals(address1)){
							if(updatedProp.getAddress2().equals(address2)){
								if(updatedProp.getAddress3().equals(city)){
									if(updatedProp.getPostcode().equals(postcode)){
										if(updatedProp.getType() == propertyType){
											if(updatedProp.getHeadline().equals(advertTitle)){
												if(updatedProp.getDescription().equals(desc)){
													if(updatedProp.getLocalArea().equals(localArea)){	
														if(updatedProp.getOccupantType().equals(occupantType)){
															if(updatedProp.getBillsIncluded() == bi){
																if(updatedProp.getPetsAllowed() == pa){
																	if(updatedProp.getParking() == p){
																		if(updatedProp.getDisabledAccess() == da){
																			if(updatedProp.getSmoking() == s){
																				if(updatedProp.getQuietArea() == qa){
																					if(updatedProp.getShortTerm() == st){
																						if(updatedProp.getSingleGender() == sg){
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
													}
												}
											}
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
		
		@When("^the save form is submitted with (\\d+), (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)$")
		public void the_save_form_is_submitted_with_and(Integer propId, Integer price, Integer houseNum, String address1, String address2, String city, String postcode, Integer fromDD, Integer fromMM, Integer fromYYYY, Integer toDD, Integer toMM, Integer toYYYY, Integer propertyType, String advertTitle, String desc, String localArea, Integer numRooms, Integer occupiedRooms, String occupantType, Integer billsIncluded, Integer petsAllowed, Integer parking, Integer disabledAccess, Integer smoking, Integer quietArea, Integer shortTerm, Integer singleGender) throws Throwable {
	
			oldProperty = propRepo.findById(propId);
			
			Boolean bi = (billsIncluded != 0);
			Boolean pa = (petsAllowed != 0);
			Boolean p = (parking != 0);
			Boolean da = (disabledAccess != 0);
			Boolean s = (smoking != 0);
			Boolean qa = (quietArea != 0);
			Boolean st = (shortTerm != 0);
			Boolean sg = (singleGender != 0);
			
			MockMvcBuilders.webAppContextSetup(this.wac).build()
		      .perform(post("https://localhost:8090/submitEditedPropertyPWOI")
		    		  	.param("id", propId.toString())
		    		  	
		    		  	.param("price", price.toString())
		    		  	.param("houseNum", houseNum.toString())
		    		  	.param("address1", address1)
		    		  	.param("address2", address2)
		    		  	.param("address3", city)
		    		  	.param("postcode", postcode)
		    		  	.param("fromDD", fromDD.toString())
		    		  	.param("fromMM", fromMM.toString())
		    		  	.param("fromYYYY", fromYYYY.toString())
		    		  	.param("toDD", toDD.toString())
		    		  	.param("toMM", toMM.toString())
		    		  	.param("toYYYY", toYYYY.toString())
		    		  	
		    		  	.param("propertytype", propertyType.toString()) 
		    		  	.param("advertTitle", advertTitle)
		    		  	.param("description", desc)
		    		  	.param("localArea", localArea)
		    		  	
		    		  	.param("occupantType", occupantType) 
		    		  	.param("billsincluded", bi.toString()) 
		    		  	.param("petsallowed", pa.toString()) 
		    		  	
		    		  	.param("parking", p.toString())
		    		  	.param("disabledaccess", da.toString()) 
		    		  	.param("smoking", s.toString()) 
		    		  	.param("quietarea", qa.toString()) 
		    		  	.param("shortterm", st.toString())
		    		  	.param("singlegender", sg.toString()) 
		    		  	
				        .param("location", "landlord-dashboard/manage")
		          .with(csrf())
		          .with(authentication(authentication)));
		}
	

		
		@Then("^we should see (\\d+), (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+) has been updated$")
		public void we_should_see_and_has_been_updated(Integer propId, Integer price, Integer houseNum, String address1, String address2, String city, String postcode, Integer fromDD, Integer fromMM, Integer fromYYYY, Integer toDD, Integer toMM, Integer toYYYY, Integer propertyType, String advertTitle, String desc, String localArea, Integer numRooms, Integer occupiedRooms, String occupantType, Integer billsIncluded, Integer petsAllowed, Integer parking, Integer disabledAccess, Integer smoking, Integer quietArea, Integer shortTerm, Integer singleGender) throws Throwable {
			
			boolean toReturn = true;
			boolean toCheck = false;
			
			Boolean bi = (billsIncluded != 0);
			Boolean pa = (petsAllowed != 0);
			Boolean p = (parking != 0);
			Boolean da = (disabledAccess != 0);
			Boolean s = (smoking != 0);
			Boolean qa = (quietArea != 0);
			Boolean st = (shortTerm != 0);
			Boolean sg = (singleGender != 0);
			
			Property updatedProp = propRepo.findById(propId);
			
			if(updatedProp.getPricePerWeek() == price){
				if(updatedProp.getHouseNum().equals(houseNum.toString())){
					if(updatedProp.getAddress1().equals(address1)){
						if(updatedProp.getAddress2().equals(address2)){
							if(updatedProp.getAddress3().equals(city)){
								if(updatedProp.getPostcode().equals(postcode)){
									if(updatedProp.getType() == propertyType){
										if(updatedProp.getHeadline().equals(advertTitle)){
											if(updatedProp.getDescription().equals(desc)){
												if(updatedProp.getLocalArea().equals(localArea)){	
													if(updatedProp.getOccupantType().equals(occupantType)){
														if(updatedProp.getBillsIncluded() == bi){
															if(updatedProp.getPetsAllowed() == pa){
																if(updatedProp.getParking() == p){
																	if(updatedProp.getDisabledAccess() == da){
																		if(updatedProp.getSmoking() == s){
																			if(updatedProp.getQuietArea() == qa){
																				if(updatedProp.getShortTerm() == st){
																					if(updatedProp.getSingleGender() == sg){
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
												}
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

}