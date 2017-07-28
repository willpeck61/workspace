package flatfinder;

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
.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet
.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet
.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

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
import flatfinder.SecurityConfig;
import flatfinder.WebConfig;
import flatfinder.FlatFinder;
import flatfinder.controller.AccountController;
import flatfinder.controller.SessionController;
import flatfinder.domain.Role;
import flatfinder.domain.User;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
    SessionController.class,
    AccountController.class,
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

  @Given("^Im am a \"([^\"]*)\" with username \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void im_am_a_with_username_and_password(String arg1, String arg2, String arg3) 
      throws Throwable {
    username = arg2;
    pw = arg3;
  }
  
  @When("^Im access \"([^\"]*)\"$")
  public void im_access(String arg1) throws Throwable {
    result = mockMvc.perform(post("https://localhost" + arg1)
        .param("username",username)
        .param("password", pw)
        .with(csrf())).andDo(print());
  }
  
  @Then("^My authentication is true with role \"([^\"]*)\"$")
  public void my_authentication_is_true_with_role(String arg1) throws Throwable {
    result.andExpect(redirectedUrl("/"));
  }
  
  @Then("^My authentication is false with role \"([^\"]*)\"$")
  public void my_authentication_is_false_with_role(String arg1) throws Throwable {
    result.andExpect(redirectedUrl("/error-login"));
  }
  
  @Given("^an developer \"([^\"]*)\"$")
  public void an_developer(String arg1) throws Throwable {
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    User alice;
    Role role;
    try {
      alice = userRepo.findByLogin(arg1);
    } catch (Exception e){
      alice = new User();
      role = new Role();
      role.setId(1);
      role.setRole("SEARCHER");
      alice.setId(1);
      alice.setLogin(arg1);
      alice.setPassword(pe.encode("password"));
      alice.setRole(role);
      userRepo.save(alice);
    }
    username = arg1;
  }
  
  @When("^Im retrieve the password from the user credentials stored in the repository$")
  public void im_retrieve_the_password_from_the_user_credentials_stored_in_the_repository() 
      throws Throwable {
    user = userRepo.findByLogin(username);
    pw = user.getPassword().toString();
  }
  
  @Then("^Im should get the encrypted password \"([^\"]*)\"$")
  public void im_should_get_the_encrypted_password(String arg1) throws Throwable {
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    assertTrue(pe.matches(arg1, pw));
  }
  
  @Given("^Im am a \"([^\"]*)\" role with username \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void im_am_a_role_with_username_and_password(String arg1, String arg2, String arg3)
      throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg2, arg3, AuthorityUtils.createAuthorityList(arg1));
  }

  @When("^Im access a \"([^\"]*)\"$")
  public void im_access_a(String arg1) throws Throwable {
    if (arg1.equals("/amend-user")) {
      result = mockMvc.perform(post("https://localhost" +arg1).with(authentication(authentication)).with(csrf())).andDo(print());
    } else {
      result = mockMvc.perform(get("https://localhost" +arg1).with(authentication(authentication))).andDo(print());
    }
    result.andReturn().getResponse();
  }
  
  @Then("^My authentication is then false with role \"([^\"]*)\" to return (\\d+)$")
  public void my_authentication_is_then_false_with_role_to_return(String arg1, int arg2) throws Throwable {
    result.andExpect(status().is(arg2));
  }

  @Then("^My authentication is then true with role \"([^\"]*)\" to return (\\d+)$")
  public void my_authentication_is_then_true_with_role_to_return(String arg1, int arg2) throws Throwable {
    result.andExpect(status().is(arg2));
  }
  
  /**
   * Delete all test data and reload saved state.
   **/
  /*
  @After
  public void revert(){
    userRepo.deleteAll();
    roleRepo.deleteAll();
    userRepo.save(userSavePoint);
    roleRepo.save(roleSavePoint);
  }*/
}
