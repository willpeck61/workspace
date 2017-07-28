package flatfinder;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flatfinder.controller.AccountController;
import flatfinder.controller.SessionController;
import flatfinder.domain.BuddyUp;
import flatfinder.domain.Notification;
import flatfinder.domain.Profile;
import flatfinder.domain.Role;
import flatfinder.domain.User;
import flatfinder.persistence.repository.BuddyUpRepository;
import flatfinder.persistence.repository.InboxRepository;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.ProfileRepository;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = {FlatFinder.class,
    SessionController.class,
    AccountController.class,
    SecurityConfig.class,
    WebConfig.class,
})

public class BuddyStepDefs {
  
  @Autowired
  private WebApplicationContext wac;
  
  @Autowired
  private Filter springSecurityFilterChain;
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private RoleRepository roleRepo;
  
  @Autowired
  private BuddyUpRepository buddyRepo;
  
  @Autowired
  private NotificationRepository notifRepo;
  
  @Autowired
  private InboxRepository inboxRepo;
  
  private Method method;
  private MockMvc mockMvc;
  private ResultActions result;
  private Authentication authentication;
  private User user;
  
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
    try {

      result = mockMvc.perform(get("https://localhost/user-login"));
      
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @Given("^im the user \"([^\"]*)\" on the profile page$")
  public void im_the_user_on_the_profile_page(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    user = userRepo.findByLogin(arg1);
  }

  @When("^im sending the buddy request to \"([^\"]*)\"$")
  public void im_sending_the_buddy_request_to(String arg1) throws Throwable {

    authentication = new UsernamePasswordAuthenticationToken(
        "Alice", "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
      result = mockMvc.perform(get("https://localhost/buddy-request")
          .param("responder", arg1)
          .with(authentication(authentication))
          .with(csrf())).andDo(print());
      result.andExpect(status().is(200));
  }

  @Then("^the buddy request for \"([^\"]*)\" to \"([^\"]*)\" should exist$")
  public void the_buddy_request_for_to_should_exist(String arg1, String arg2) throws Throwable {
      User init = userRepo.findByLogin(arg1);
      User resp = userRepo.findByLogin(arg2);
      assertNotNull(buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId()));
  }

  @Given("^im the user \"([^\"]*)\" on the notification page$")
  public void im_the_user_on_the_notification_page(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    user = userRepo.findByLogin(arg1);
  }

  @When("^click the notification from \"([^\"]*)\" to buddyup$")
  public void click_the_notification_from_to_buddyup(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        "Adam", "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User init = userRepo.findByLogin(arg1);
    User resp = userRepo.findByLogin(authentication.getName());
    result = mockMvc.perform(get("https://localhost/notifications")
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(200));
    List<Notification> notifs = notifRepo.findByForUser(authentication.getName());
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId());
    Boolean flag = false;
    for (Notification n : notifs) {
      if (n.getRequestId() == bup.getId()) {
        flag = true;
      }
    }
  }

  @Then("^the notification for \"([^\"]*)\" is diaplayed$")
  public void the_notification_for_is_diaplayed(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User init = userRepo.findByLogin("alice");
    User resp = userRepo.findByLogin(arg1);
    assertNotNull(resp);
    assertNotNull(init);
    List<Notification> notifs = notifRepo.findByForUser(arg1);
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId());
    result = mockMvc.perform(get("https://localhost/notifications")
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(200));
  }

  @When("^ive clicked to accept the buddy request from \"([^\"]*)\"$")
  public void ive_clicked_to_accept_the_buddy_request_from(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User init = userRepo.findByLogin(arg1);
    User resp = userRepo.findByLogin("Adam");
    result = mockMvc.perform(get("https://localhost/buddy-request")
        .param("responder", resp.getLogin())
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(200));
    List<Notification> notifs = notifRepo.findByForUser("Adam");
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId());
    assertNotNull(bup);
    for (Notification n : notifs) {
      if (n.getRequestId() == bup.getId()) {
        result = mockMvc.perform(get("https://localhost/action-buddy-request")
            .param("notifyid", n.getId().toString())
            .param("requestid", bup.getId().toString())
            .param("response", "accept")
            .with(authentication(authentication))
            .with(csrf())).andDo(print());
        result.andExpect(status().is(302));
      }
    }
  }

  @Then("^the user \"([^\"]*)\" is a confirmed buddy$")
  public void the_user_is_a_confirmed_buddy(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User init = userRepo.findByLogin(arg1);
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), userRepo.findByLogin(authentication.getName()).getId());
  }

  @When("^ive clicked to reject the buddy request from \"([^\"]*)\"$")
  public void ive_clicked_to_reject_the_buddy_request_from(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User init = userRepo.findByLogin(arg1);
    User resp = userRepo.findByLogin("Adam");
    List<Notification> notifs = notifRepo.findByForUser("Adam");
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId());
    for (Notification n : notifs) {
      if (n.getRequestId() == bup.getId()) {
        result = mockMvc.perform(get("https://localhost/action-buddy-request")
            .param("notifyid", n.getId().toString())
            .param("requestid", bup.getId().toString())
            .param("response", "reject")
            .with(authentication(authentication))
            .with(csrf())).andDo(print());
        result.andExpect(status().is(200));
      }
    }
  }

  @Then("^the user \"([^\"]*)\" is not a confirmed buddy$")
  public void the_user_is_not_a_confirmed_buddy(String arg1) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User init = userRepo.findByLogin(arg1);
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), userRepo.findByLogin(authentication.getName()).getId());
    assertNotNull(bup);
  }

  @Given("^Im logged in as user id (\\d+)$")
  public void im_logged_in_as_user_id(int arg1) throws Throwable {
    User user = userRepo.findById(arg1);
    authentication = new UsernamePasswordAuthenticationToken(
        user.getLogin(), "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
  }

  @When("^made buddy request to user id (\\d+) from (\\d+)$")
  public void made_buddy_request_to_user_id_from(int arg1, int arg2) throws Throwable {
    result = mockMvc.perform(get("https://localhost/buddy-request")
        .param("responder", userRepo.findById(arg1).getLogin())
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(200));
  }

  @Then("^after the (\\d+) and (\\d+) the total buddy requests are (\\d+) when the response is true$")
  public void after_the_and_the_total_buddy_requests_are_when_the_response_is_true(int arg1, int arg2, int arg3) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User resp = userRepo.findById(arg1);
    User init = userRepo.findById(arg2);
    List<Notification> notifs = notifRepo.findByForUser(resp.getLogin());
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId());
    for (Notification n : notifs) {
      if (n.getRequestId() == bup.getId()) {
        result = mockMvc.perform(get("https://localhost/action-buddy-request")
            .param("notifyid", n.getId().toString())
            .param("requestid", bup.getId().toString())
            .param("response", "accept")
            .with(authentication(authentication))
            .with(csrf())).andDo(print());
        result.andExpect(status().is(302));
        buddyRepo.delete(bup.getId());;
        notifRepo.delete(n.getId());
      }
    }
    List<BuddyUp> buddies = buddyRepo.findByInitialiserIdOrResponderId(resp.getId(), resp.getId());
  }

  @Then("^after the (\\d+) and (\\d+) the total buddy requests are (\\d+) when the response is false$")
  public void after_the_and_the_total_buddy_requests_are_when_the_response_is_false(int arg1, int arg2, int arg3) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User resp = userRepo.findById(arg1);
    User init = userRepo.findById(arg2);
    List<Notification> notifs = notifRepo.findByForUser(resp.getLogin());
    BuddyUp bup = buddyRepo.findByInitialiserIdAndResponderId(init.getId(), resp.getId());
    for (Notification n : notifs) {
      if (n.getRequestId() == bup.getId()) {
        result = mockMvc.perform(get("https://localhost/action-buddy-request")
            .param("notifyid", n.getId().toString())
            .param("requestid", bup.getId().toString())
            .param("response", "reject")
            .with(authentication(authentication))
            .with(csrf())).andDo(print());
        result.andExpect(status().is(302));
        buddyRepo.delete(bup.getId());;
        notifRepo.delete(n.getId());
      }
    }
    List<BuddyUp> buddies = buddyRepo.findByInitialiserIdOrResponderId(resp.getId(), resp.getId()); 
  }
  
  @Given("^user \"([^\"]*)\" has a buddy \"([^\"]*)\"$")
  public void user_has_a_buddy(String arg1, String arg2) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
      User usr = userRepo.findByLogin(arg1);
      User bud = userRepo.findByLogin(arg2);
      System.out.println("USER:" + usr.getId());
      System.out.println("RESP:" + bud.getId());
      result = mockMvc.perform(get("https://localhost/buddy-request")
          .param("responder", bud.getLogin())
          .with(authentication(authentication))
          .with(csrf())).andDo(print());
      result.andExpect(status().is(200));
      BuddyUp budchk = buddyRepo.findByInitialiserIdAndResponderId(usr.getId(), bud.getId());
      assertNotNull(budchk);
  }

  @When("^user \"([^\"]*)\" removes \"([^\"]*)\" as a buddy$")
  public void user_removes_as_a_buddy(String arg1, String arg2) throws Throwable {
    authentication = new UsernamePasswordAuthenticationToken(
        arg1, "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    User usr = userRepo.findByLogin(arg1);
    User bud = userRepo.findByLogin(arg2);
    BuddyUp budchk = buddyRepo.findByInitialiserIdAndResponderId(usr.getId(), bud.getId());
    assertNotNull(budchk);
    Notification notify = notifRepo.findByRequestId(budchk.getId());
    assertNotNull(notify);
    result = mockMvc.perform(get("https://localhost/remove-buddy")
        .param("username", arg2)
        .param("notifyid", notify.getId().toString())
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(302));
  }

  @Then("^user \"([^\"]*)\" is no longer buddies with \"([^\"]*)\"$")
  public void user_is_no_longer_buddies_with(String arg1, String arg2) throws Throwable {
    User usr = userRepo.findByLogin(arg1);
    User bud = userRepo.findByLogin(arg2);
    BuddyUp budchk = buddyRepo.findByInitialiserIdAndResponderId(usr.getId(), bud.getId());
    assertNull(budchk);
  }

  @When("^user (\\d+) removes buddy up with (\\d+)$")
  public void user_removes_buddy_up_with(int arg1, int arg2) throws Throwable {
    User usr = userRepo.findById(arg1);
    assertNotNull(usr);
    User bud = userRepo.findById(arg2);
    assertNotNull(bud);
    result = mockMvc.perform(get("https://localhost/buddy-request")
        .param("responder", bud.getLogin())
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(200));
    authentication = new UsernamePasswordAuthenticationToken(
        usr.getLogin(), "password", AuthorityUtils.createAuthorityList("ROLE_SEARCHER"));
    BuddyUp budchk = buddyRepo.findByInitialiserIdAndResponderId(usr.getId(), bud.getId());
    assertNotNull(budchk);
    Notification notify = notifRepo.findByRequestId(budchk.getId());
    assertNotNull(notify);
    result = mockMvc.perform(get("https://localhost/remove-buddy")
        .param("username", bud.getLogin())
        .param("notifyid", notify.getId().toString())
        .with(authentication(authentication))
        .with(csrf())).andDo(print());
    result.andExpect(status().is(302));
  }

  @Then("^after (\\d+) and (\\d+) are no longer buddies$")
  public void after_and_are_no_longer_buddies(int arg1, int arg2) throws Throwable {
    User usr = userRepo.findById(arg1);
    User bud = userRepo.findById(arg2);
    BuddyUp budchk = buddyRepo.findByInitialiserIdAndResponderId(usr.getId(), bud.getId());
    assertNull(budchk);
  }
  
  @When("^I mass message all users with the message \"([^\"]*)\"$")
  public void i_mass_message_all_users_with_the_message(String arg1) throws Throwable {
	  result = mockMvc.perform(post("/admin-dashboard/users/message-all/message")
			  .param("message", arg1)
			  .with(authentication(authentication))
			  .with(csrf())).andDo(print());
	  result.andExpect(status().is(302));
  }

  @Then("^they should receive the message from \"([^\"]*)\"$")
  public void they_should_receive_the_message(String arg1) throws Throwable {
	  boolean received = true;
	  if(inboxRepo.findBySender(arg1).size() < 0){
		  received = false;
	  }
	  assertTrue(received);
  }

  @Then("^receive a notification$")
  public void receive_a_notification() throws Throwable {
	  boolean notified = false;
	  if(((List<Notification>) notifRepo.findAll()).size() > 0){
		  notified = true;
	  }
	  assertTrue(notified);
  }
  
  @When("^I send \"([^\"]*)\" a message with the message \"([^\"]*)\"$")
  public void i_send_a_message_with_the_message(String toUser, String message) throws Throwable {
	  result = mockMvc.perform(post("/sendInitMessage")
			  .param("userToSend", toUser)
			  .param("messageContent", message)
			  .with(authentication(authentication))
			  .with(csrf())).andDo(print());
	  result.andExpect(status().is(302));
  }

  @Then("^\"([^\"]*)\" should receive the message from \"([^\"]*)\"$")
  public void should_receive_the_message_from(String toUser, String message) throws Throwable {
	  boolean received = true;
	  if(inboxRepo.findByReceiver(toUser).size() < 0){
		  received = false;
	  }
	  assertTrue(received);
  }
}
