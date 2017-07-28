package flatfinder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

public class RegisterUserStepDef {
  
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
    userSavePoint = userRepo.findAll();
    roleSavePoint = roleRepo.findAll();
  }
  
  @Given("^im completing the \"([^\"]*)\" form$")
  public void im_completing_the_form(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        "alice", "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    result = mockMvc.perform(get("/" + arg1));
    result.andExpect(redirectedUrl("https://localhost/" + arg1));
    testUrl = arg1;
  }
  
  @When("^the Register form is submitted with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
  public void the_Register_form_is_submitted_with_and (
      String arg1, String arg2, String arg3, String arg4, 
      String arg5, String arg6, String arg7, String arg8, String arg9) throws Throwable {
    result = mockMvc.perform(post("https://localhost:8090/addUser")
        .param("firstname", arg1)
        .param("lastname", arg2)
        .param("address1", arg3)
        .param("address2", arg4)
        .param("address3", arg5)
        .param("postcode", arg6)
        .param("role", arg7)
        .param("username", arg8)
        .param("p1", arg9) 
        .param("email","test@test.com")
        .param("agegroup", "1")
        .param("title", "mr")
        .param("status", "1")
        .param("buddyup", "1")
        .param("secret", "secret")
        .param("answer", "answer")
        .with(csrf())).andDo(print());
  }

  @Then("^we should see that \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" has been registered$")
  public void we_should_see_that_and_has_been_registered(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9) throws Throwable {
    User user = userRepo.findByLogin(arg1);
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    assertNotNull(user);
    assertNotNull(user.getPassword());
    assertEquals(user.getFirstName(),arg1);
    assertEquals(user.getLastName(),arg2);
    assertEquals(user.getAddress1(),arg3);
    assertEquals(user.getAddress2(),arg4);
    assertEquals(user.getAddress3(),arg5);
    assertEquals(user.getPostcode(),arg6);
    assertEquals(user.getLogin(),arg8);
  }

  @Then("^the folder structure for \"([^\"]*)\" has been created$")
  public void the_folder_structure_for_has_been_created(String arg1) throws Throwable {
      File profileFolder = new File("./src/main/webapp/resources/users/" + arg1);
      assertTrue(profileFolder.exists());
  }
  
  @Given("^im completing the registration form with the username \"([^\"]*)\"$")
  public void im_completing_the_registration_form_with_the_username(String arg1) throws Throwable {
   //This is not required as test data is populated in When step. ref Will.
  }

  @Then("^an error message \"([^\"]*)\" is returned$")
  public void an_error_message_is_returned(String arg1) throws Throwable {
      result.andExpect(status().is(400));
  }

  @Given("^the registered user \"([^\"]*)\" not logged in for (\\d+) days$")
  public void the_registered_user_not_logged_in_for_days(String arg1, int arg2) throws Throwable {
      User user = userRepo.findByLogin(arg1);
      user.setLastActive(TimeUnit.DAYS.toMillis(arg2));
      userRepo.save(user);
  }

  @When("^the landing page is accessed$")
  public void the_landing_page_is_accessed() throws Throwable {
    result = mockMvc.perform(get("https://localhost/user-login")).andDo(print());
    result.andExpect(status().is(200));
  }

  @Then("^the user \"([^\"]*)\" is suspended$")
  public void the_user_is_suspended(String arg1) throws Throwable {
      User user = userRepo.findByLogin(arg1);
      authentication = new UsernamePasswordAuthenticationToken(
          arg1, "password", AuthorityUtils.createAuthorityList(user.getRole().getRole()));
      result = mockMvc.perform(get("https://localhost/view-profile")
          .with(authentication(authentication))
          .with(csrf())).andDo(print());
      result.andExpect(status().is(403));
  }
  
  @Given("^the registered user \"([^\"]*)\" has not logged in for (\\d+) daya$")
  public void the_registered_user_has_not_logged_in_for_daya(String arg1, int arg2) throws Throwable {
    User user = userRepo.findByLogin(arg1);
    user.setLastActive(TimeUnit.DAYS.toMillis(arg2));
    userRepo.save(user);
  }

  @Then("^the user \"([^\"]*)\" is not suspended$")
  public void the_user_is_not_suspended(String arg1) throws Throwable {
    User user = userRepo.findByLogin(arg1);
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    result = mockMvc.perform(get("https://localhost/admin-dashboard/users")
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(200));
  }

  @When("^the lsnding page is accessed$")
  public void the_lsnding_page_is_accessed() throws Throwable {
    result = mockMvc.perform(get("https://localhost/user-login")).andDo(print());
    result.andExpect(status().is(200));
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
