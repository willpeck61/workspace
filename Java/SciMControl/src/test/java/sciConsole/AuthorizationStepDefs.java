package sciConsole;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet
.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet
.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet
.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet
.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet
.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication
.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.After;
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
import sciConsole.domain.Role;
import sciConsole.domain.User;
import sciConsole.persistence.repository.RoleRepository;
import sciConsole.persistence.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {SciConsoleApplication.class,
								 SessionController.class,
								 AccountController.class,
								 ChangeRequestController.class,
								 SecurityConfig.class,
								 WebConfig.class})

public class AuthorizationStepDefs {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Filter springSecurityFilterChain;
	private MockMvc mockMvc;
	private ResultActions result;
	private Authentication authentication;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	private String username;
	private User user;
	private String pw;

	private Iterable<Role> roleSavePoint;

	private Iterable<User> userSavePoint;
	
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

	@Given("^I am a \"([^\"]*)\" with username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_am_a_with_username_and_password(String arg1, String arg2, String arg3) throws Throwable {
	    authentication = new UsernamePasswordAuthenticationToken(arg2, arg3, AuthorityUtils.createAuthorityList("ROLE_" + arg1));
	}
	
	@When("^I access \"([^\"]*)\"$")
	public void i_access(String arg1) throws Throwable {
	    result=mockMvc.perform(get(arg1).with(authentication(authentication)));
	}
	
	@Then("^My authentication is true with role \"([^\"]*)\"$")
	public void my_authentication_is_true_with_role(String arg1) throws Throwable {
	    result.andExpect(authenticated().withRoles(arg1));
	}
	
	@Then("^My authentication is false with role \"([^\"]*)\"$")
	public void my_authentication_is_false_with_role(String arg1) throws Throwable {
	    result.andExpect(redirectedUrl("https://localhost/login-form"));
	}
	
	@Given("^a developer \"([^\"]*)\"$")
	public void a_developer(String arg1) throws Throwable {
		BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
		User alice;
		Role role;
		try {
			alice = userRepo.findByLogin(arg1);
		} catch (Exception e){
			alice = new User();
			role = new Role();
			role.setId(1);
			role.setRole("DEVELOPER");
			alice.setId(1);
			alice.setLogin(arg1);
			alice.setPassword(pe.encode("password"));
			alice.setRole(role);
			userRepo.save(alice);
		}	
		username = arg1;
	}

	@When("^I retrieve the password from the user credentials stored in the repository$")
	public void i_retrieve_the_password_from_the_user_credentials_stored_in_the_repository() throws Throwable {	
		user = userRepo.findByLogin(username);
		pw = user.getPassword().toString();
	}

	@Then("^I should get the encrypted password \"([^\"]*)\"$")
	public void i_should_get_the_encrypted_password(String arg1) throws Throwable {
		BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
		assertTrue(pe.matches("password", pw));	
	}
	
	@Given("^I am a \"([^\"]*)\" role with username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_am_a_role_with_username_and_password(String arg1, String arg2, String arg3) throws Throwable {
		authentication = new UsernamePasswordAuthenticationToken(arg2, arg3, AuthorityUtils.createAuthorityList("ROLE_" + arg1));
	}
	
	@When("^I access a \"([^\"]*)\"$")
	public void i_access_a(String arg1) throws Throwable {
	    result = mockMvc.perform(get("/assess-change-request").with(authentication(authentication)));
	}
	
	@Then("^My authentication is then false with role \"([^\"]*)\"$")
	public void my_authentication_is_then_false_with_role(String arg1) throws Throwable {
		result.andExpect(status().is3xxRedirection());
	    result.andExpect(redirectedUrl("https://localhost/assess-change-request"));
	}

	@Then("^My authentication is then true with role \"([^\"]*)\"$")
	public void my_authentication_is_then_true_with_role(String arg1) throws Throwable {
		result.andExpect(authenticated().withRoles(arg1));
	}
	
	@After
	public void revert(){
		userRepo.deleteAll();
		roleRepo.deleteAll();
		userRepo.save(userSavePoint);
		roleRepo.save(roleSavePoint);
	}
}
