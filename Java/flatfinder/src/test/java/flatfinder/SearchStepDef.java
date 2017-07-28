package flatfinder;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
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
import flatfinder.domain.Property;
import flatfinder.persistence.repository.PropertyRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
    SessionController.class,
    AccountController.class,
    SecurityConfig.class,
    WebConfig.class})

public class SearchStepDef {
  
  @Autowired
  private WebApplicationContext wac;
  
  @Autowired
  private Filter springSecurityFilterChain;
  
  @Autowired
  private PropertyRepository propRepo;

  
  private MockMvc mockMvc;
  private ResultActions result;
  
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
	
	@Given("^im searching for \"([^\"]*)\"$")
	  public void im_searching_for(String arg1) throws Throwable {
		  result = mockMvc.perform(post("/search")
					.param("postcode", arg1)
					.param("minPrice", "0")
					.param("maxPrice", "10000")
					.param("propType", "1")
					.param("minNumRooms", "0")
					.param("maxNumRooms", "0"));

		  result.andExpect(redirectedUrl("https://localhost/search"));
		  testUrl = arg1;
	  }

	@When("^ive submitted the search form$")
	  public void ive_submitted_the_search_form() throws Throwable {
		  result.andExpect(redirectedUrl("https://localhost/search"));
	  }

	@Then("^the search page is displayed with \"([^\"]*)\" as the result$")
	  public void the_search_page_is_displayed_with_as_the_result(String arg1) throws Throwable {
			List<Property> allProperties = (List<Property>) propRepo.findAll();
		  	boolean toReturn = true;
		  	boolean toCheck = false;
		  	
		  	
			for (int i = 0; i < allProperties.size(); i++) {
				if(toCheck == false){
					if(allProperties.get(i).getPostcode().equals(arg1)){
							toCheck = true;
					}else{
						toCheck = false;
					}
				}
			}

			assertEquals(toReturn,toCheck);
	  }

	@Given("^im searching for \"([^\"]*)\" with (\\d+) bedrooms max$")
	public void im_searching_for_with_bedrooms_max(String postcode, Integer numRooms) throws Throwable {
		result = mockMvc.perform(post("/search")
				.param("postcode", postcode)
				.param("minPrice", "0")
				.param("maxPrice", "0")
				.param("propType", "1")
				.param("minNumRooms", "0")
				.param("maxNumRooms", "12"));
	  
	  result.andExpect(redirectedUrl("https://localhost/search"));
	  testUrl = postcode;
	}

	@Then("^the search page is displayed with \"([^\"]*)\" and max (\\d+) bedrooms$")
	public void the_search_page_is_displayed_with_and_max_NO_OF_ROOMS_bedrooms(String postcode, int numRooms) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findAll();
	  	boolean toReturn = true;
	  	boolean toCheck = false;
	  	
	  	
		for (int i = 0; i < allProperties.size(); i++) {	
			if(toCheck == false){
				if(allProperties.get(i).getPostcode().equals(postcode)){
					if(allProperties.get(i).getNumberOfRooms() <= numRooms){
						toCheck = true;
					}else{
						toCheck = false;
					}
				}else{
					toCheck = false;
				}
			}
		}

		assertEquals(toReturn,toCheck);
	}

	@Then("^the search page is displayed with no results with postcode \"([^\"]*)\"$")
	public void the_search_page_is_displayed_with_no_results_with_postcode(String postcode) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findAll();
	  	boolean toReturn = false;
	  	boolean toCheck = false;
	  	
		for (int i = 0; i < allProperties.size(); i++) {
			if(toCheck == true){
				if(allProperties.get(i).getPostcode().equals(postcode)){
						toCheck = false;
				}else{
					toCheck = true;
				}
			}
		}

		assertEquals(toReturn,toCheck);
	}
	
	@Given("^im searching for \"([^\"]*)\" with (\\d+) bedrooms min and (\\d+) bedrooms max$")
	public void im_searching_for_with_bedrooms_min_and_bedrooms_max(String postcode, Integer minRooms, Integer maxRooms) throws Throwable {
		result = mockMvc.perform(post("/search")
				.param("postcode", postcode)
				.param("minPrice", "0")
				.param("maxPrice", "10000")
				.param("propType", "1")
				.param("minNumRooms", minRooms.toString())
				.param("maxNumRooms", maxRooms.toString()));
	  
	  result.andExpect(redirectedUrl("https://localhost/search"));
	  testUrl = postcode;
	}

	@Then("^the search page is displayed with \"([^\"]*)\" and min (\\d+) bedrooms and max (\\d+) bedrooms$")
	public void the_search_page_is_displayed_with_and_min_bedrooms_and_max_bedrooms(String postcode, Integer minRooms, Integer maxRooms) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findAll();
	  	boolean toReturn = true;
	  	boolean toCheck = false;
	  	
		for (int i = 0; i < allProperties.size(); i++) {
			if(toCheck == false){
				if(allProperties.get(i).getPostcode().equals(postcode)){
					if(allProperties.get(i).getNumberOfRooms() >= minRooms && allProperties.get(i).getNumberOfRooms() <= maxRooms){
						toCheck = true;
					}						
				}
			}
		}

		assertEquals(toReturn,toCheck);
	}

	@Given("^im searching for \"([^\"]*)\" with (\\d+) min price and (\\d+) max price$")
	public void im_searching_for_with_min_price_and_max_price(String postcode, Integer minPrice, Integer maxPrice) throws Throwable {
		result = mockMvc.perform(post("/search")
				.param("postcode", postcode)
				.param("minPrice", minPrice.toString())
				.param("maxPrice", maxPrice.toString())
				.param("propType", "1")
				.param("minNumRooms", "0")
				.param("maxNumRooms", "0"));
	  
	  result.andExpect(redirectedUrl("https://localhost/search"));
	  testUrl = postcode;
	}
	
	@Then("^the search page is displayed with \"([^\"]*)\" and (\\d+) min price and (\\d+) max price$")
	public void the_search_page_is_displayed_with_and_min_price_and_max_price(String postcode, Integer minPrice, Integer maxPrice) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findAll();
	  	boolean toReturn = true;
	  	boolean toCheck = false;
	  	
		for (int i = 0; i < allProperties.size(); i++) {
			if(toCheck == false){
				if(allProperties.get(i).getPostcode().equals(postcode)){
					if(allProperties.get(i).getPricePerWeek() >= minPrice && allProperties.get(i).getPricePerWeek() <= maxPrice){
						toCheck = true;
					}						
				}
			}
		}

		assertEquals(toReturn,toCheck);
	}

	
	@Given("^im searching for \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void im_searching_for_and(String postcode, String address, String city) throws Throwable {
		result = mockMvc.perform(post("/search")
				.param("postcode", postcode)
				.param("address", address)
				.param("city", city)
				.param("minPrice", "0")
				.param("maxPrice", "0")
				.param("propType", "1")
				.param("minNumRooms", "0")
				.param("maxNumRooms", "0"));
	  
	  result.andExpect(redirectedUrl("https://localhost/search"));
	}

	@Then("^the search page is displayed with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void the_search_page_is_displayed_with_and(String postcode, String address, String city) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findAll();
	  	boolean toReturn = true;
	  	boolean toCheck = false;
	  	
		for (int i = 0; i < allProperties.size(); i++) {
			if(toCheck == false){
				if(allProperties.get(i).getPostcode().equals(postcode)){
					if(allProperties.get(i).getAddress1().equals(address)){
						if(allProperties.get(i).getAddress3().equals(city)){
							toCheck = true;
						}
					}											
				}
			}
		}

		assertEquals(toReturn,toCheck);
	}

	@Given("^im searching for \"([^\"]*)\" with (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)$")
	public void im_searching_for_with_and(String city, Integer bills_included, Integer disabled_access, Integer parking, Integer quiet_area, Integer short_term, Integer single_gender, Integer smoking) throws Throwable {
		
		Boolean bi 	= (bills_included 	!= 0);
		Boolean da 	= (disabled_access 	!= 0);
		Boolean p 	= (parking 			!= 0);
		Boolean qa 	= (quiet_area 		!= 0);
		Boolean st 	= (short_term 		!= 0);
		Boolean sg 	= (single_gender 	!= 0);
		Boolean s 	= (smoking 			!= 0);
		
		result = mockMvc.perform(post("/search")
				.param("city", 			city)
				.param("minPrice", 		"0")
				.param("maxPrice", 		"0")
				.param("propType", 		"1")
				.param("minNumRooms",	"0")
				.param("maxNumRooms", 	"0")
				
				.param("bills_included", 	bi.toString())
				.param("disabled_access", 	da.toString())
				.param("parking", 			p.toString())
				.param("quiet_area", 		qa.toString())
				.param("short_term", 		st.toString())
				.param("single_gender", 	sg.toString())
				.param("smoking", 			s.toString()));
	  
	  result.andExpect(redirectedUrl("https://localhost/search"));
	}
	
	@Then("^the search page is displayed with \"([^\"]*)\" with (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+) and (\\d+)$")
	public void the_search_page_is_displayed_with_with_and(String city, Integer bills_included, Integer disabled_access, Integer parking, Integer quiet_area, Integer short_term, Integer single_gender, Integer smoking) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findByAddress3(city);
	  	boolean toReturn = true;
	  	boolean toCheck = false;
	  	
	  	Boolean bi 	= (bills_included 	!= 0);
		Boolean da 	= (disabled_access 	!= 0);
		Boolean p 	= (parking 			!= 0);
		Boolean qa 	= (quiet_area 		!= 0);
		Boolean st 	= (short_term 		!= 0);
		Boolean sg 	= (single_gender 	!= 0);
		Boolean s 	= (smoking 			!= 0);
	  	
		for (int i = 0; i < allProperties.size(); i++) {
			if(toCheck == false){
				if(allProperties.get(i).getAddress3().equals(city)){
					if(allProperties.get(i).getBillsIncluded() == bi){
						if(allProperties.get(i).getDisabledAccess() == da){
							if(allProperties.get(i).getParking() == p){
								if(allProperties.get(i).getQuietArea() == qa){
									if(allProperties.get(i).getShortTerm() == st){
										if(allProperties.get(i).getSingleGender() == sg){
											if(allProperties.get(i).getSmoking() == s){
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

		assertEquals(toReturn,toCheck);
	}

	@Given("^im searching for \"([^\"]*)\" that is in between \"([^\"]*)\" and \"([^\"]*)\"$")
	public void im_searching_for_that_is_in_between_and(String city, String fromDate, String toDate) throws Throwable {
		result = mockMvc.perform(post("/search")
				.param("city", 			city)
				.param("minPrice", 		"0")
				.param("maxPrice", 		"0")
				.param("propType", 		"1")
				.param("minNumRooms",	"0")
				.param("maxNumRooms", 	"0")
				
				.param("startDate", 	fromDate)
				.param("endDate", 		toDate));
	  
	  result.andExpect(redirectedUrl("https://localhost/search"));
	}

	@Then("^the search page is displayed with \"([^\"]*)\" that is in between \"([^\"]*)\" and \"([^\"]*)\"$")
	public void the_search_page_is_displayed_with_that_is_in_between_and(String city, String fromDate, String toDate) throws Throwable {
		List<Property> allProperties = (List<Property>) propRepo.findByAddress3(city);
	  	boolean toReturn = true;
	  	boolean toCheck = false;
	  	
	  	DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	  	Date toStringDate = format.parse(fromDate);
	  	Date fromStringDate = format.parse(toDate);
	  	
		for (int i = 0; i < allProperties.size(); i++) {
			if(toCheck == false){
				if(allProperties.get(i).getAddress3().equals(city)){
					if (toStringDate.before(allProperties.get(i).getStartDate()) ||
							toStringDate.before(allProperties.get(i).getStartDate())) {
						if (fromStringDate.after(allProperties.get(i).getEndDate()) ||
								fromStringDate.after(allProperties.get(i).getEndDate())) {
							toCheck = true;
						}					
					}					
				}
			}
		}

		assertEquals(toReturn,toCheck);
	}
}