package flatfinder.controller;

import flatfinder.FlatFinder;
import flatfinder.domain.BuddyUp;
import flatfinder.domain.Interest;
import flatfinder.domain.Role;
import flatfinder.domain.User;
import flatfinder.domain.UserPropertyHistory;
import flatfinder.domain.Notification;
import flatfinder.domain.Profile;
import flatfinder.domain.Property;
import flatfinder.domain.PropertyFeedback;
import flatfinder.domain.PropertyInterest;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserPropertyHistoryRepository;
import flatfinder.persistence.repository.UserRepository;
import flatfinder.persistence.repository.BuddyUpRepository;
import flatfinder.persistence.repository.InterestRepository;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.ProfileRepository;
import flatfinder.persistence.repository.PropertyFeedbackRepository;
import flatfinder.persistence.repository.PropertyInterestRepository;
import flatfinder.persistence.repository.PropertyRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Added this comment to test the continuous integration setup
 * is now working.
 * @author willp
 */
@Controller
public class AccountController {
  @Autowired
  private InterestRepository interRepo;
  
  @Autowired
  private BuddyUpRepository buddyRepo;
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private RoleRepository roleRepo;
  
  @Autowired
  private NotificationRepository notifyRepo;
  
  @Autowired
  private ProfileRepository profileRepo;
  
  @Autowired
  private PropertyInterestRepository propintRepo;
  
  @Autowired
  private PropertyRepository propRepo;
  
  @Autowired
  private PropertyFeedbackRepository feedRepo;
 
  @Autowired
  private UserPropertyHistoryRepository userPropertyHistoryRepo;
  
  /**
   * Register a new user.
   * @param request http servlet request
   * @param rol user role
   * @param title mr mrs etc
   * @param firstname first name
   * @param lastname last name
   * @param gender male or female
   * @param status employment status
   * @param address1 house name number
   * @param address2 street
   * @param address3 town
   * @param postcode post code
   * @param password password
   * @param buddyup buddy up true of false
   * @param username user name
   * @return
   **/
  @RequestMapping(value = "/addUser", method = RequestMethod.POST)
  public ModelAndView addUser(
      @RequestParam(value = "role", required = true) Integer rol,
      @RequestParam(value = "title", required = true) String title,
      @RequestParam(value = "gender", required = false) String gender,
      @RequestParam(value = "firstname", required = true) String firstname,
      @RequestParam(value = "lastname", required = true) String lastname,
      @RequestParam(value = "agegroup", required = true) Integer agegroup,
      @RequestParam(value = "status", required = true) Integer status,
      @RequestParam(value = "address1", required = true) String address1,
      @RequestParam(value = "address2", required = true) String address2,
      @RequestParam(value = "address3", required = true) String address3,
      @RequestParam(value = "postcode", required = true) String postcode,
      @RequestParam(value = "email", required = true) String email,
      @RequestParam(value = "p1", required = true) String password,
      @RequestParam(value = "buddyup", required = false) Integer buddyup,
      @RequestParam(value = "username", required = true) String username,
      @RequestParam(value = "studyplace", required = false) String studyplace,
      @RequestParam(value = "sp-postcode", required = false) String spostcode,
      @RequestParam(value = "workplace", required = false) String workplace,
      @RequestParam(value = "wp-postcode", required = false) String wpostcode,
      @RequestParam(value = "secret", required = true) String secret,
      @RequestParam(value = "answer", required = true) String answer
		  ) {
    
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    System.out.println("Adding new user...");
    User user = new User();
    User validate = userRepo.findByLogin(username);
    List<String> success = new ArrayList<String>();
    if (validate == null) {
      user.setLogin(username);
      user.setPassword(pe.encode(password));
      user.setTitle(title);
      user.setFirstName(firstname);
      user.setLastName(lastname);
      user.setAgeGroup(agegroup);
      user.setStatus(status);
      user.setAddress1(address1);
      user.setAddress2(address2);
      user.setAddress3(address3);
      user.setPostcode(postcode);
      user.setEmail(email);
      user.setTitle(title);
      user.setGender(0);
      user.setDateCreated();
      user.setSecret(secret);
      user.setAnswer(answer);
      
      if (title.equals("Mrs")) {
        user.setGender(0);
      } else if (title.equals("Mr")) {
        user.setGender(1);
      }
      if (buddyup == null || buddyup == 0) {
        buddyup = 0;
      }
      user.setBuddyUp(buddyup);
      user.setRole(roleRepo.findById(rol));  
      userRepo.save(user);
      
      user = userRepo.findByLogin(username);
      
      if (null == profileRepo.findByUserid(user.getId())) {
        Profile profile = new Profile();
        profile.setFirstEdit(0);
        profile.setAbout("Paragraph About You");
        profile.setEnsuite(0);
        profile.setFacebookurl("Facebook URL");
        profile.setGoogleplusurl("Google Plus URL");
        profile.setHeadline("One Liner");
        profile.setInterests("Your Interests");
        profile.setLinkedinurl("LinkedIn URL");
        profile.setInterests("Your Hobbies / Interests");
        profile.setMaxsharers(0);
        profile.setPets(0);
        profile.setSmoking(0);
        profile.setTwitterurl("Twitter URL");
        profile.setQuietorlively(0);
        profile.setSamesexormixed(0);
        profile.setStudying("What are you Studying?");
        profile.setStudyyear(0);
        if ((null == workplace) || workplace.equals("")) {} else {profile.setWorkplace(workplace);};
        if ((null == spostcode) || spostcode.equals("")) {} else {profile.setSpostcode(spostcode);};
        if ((null == studyplace) || studyplace.equals("")) {} else {profile.setStudyplace(studyplace);};
        if ((null == wpostcode) || wpostcode.equals("")) {} else {profile.setWpostcode(wpostcode);};
        profile.setUserid(user.getId());
        profileRepo.save(profile);
      }
      System.out.println("Added New User");
      new File(FlatFinder.ROOT + "/users/" + username).mkdirs();
      success.add("Your account has been created.");
      File sourceF = new File(FlatFinder.ROOT + "/img/female.png");
      File sourceM = new File(FlatFinder.ROOT + "/img/male.png");
      File dest = new File(
          FlatFinder.ROOT + "/users/" + username + "/" + username + "_profile.jpg");
      try {
        if (user.getGender() == 0) {
          FileCopyUtils.copy(sourceF, dest);
        } else {
          FileCopyUtils.copy(sourceM, dest);
        }
      } catch (IOException e) {
          e.printStackTrace();
      }
    } else {
      success.add("Username Already Exists - Please Try Again.");
      System.out.println("Cannot add user. Username already exists");
    }
    ModelAndView view = new ModelAndView("register-form");
    view.addObject("success", success);
    return view;
  }
  
  @RequestMapping(value="/request-new-password", method = RequestMethod.POST)
  public ModelAndView successfulPasswordReset(
		  @RequestParam(value="email", required=true) String email
		  ){
	  ModelAndView modelAndView;
	  if(userRepo.findByEmail(email) != null && !userRepo.findByEmail(email).equals("")){
		  return new ModelAndView("forgot-successful").addObject("email", email);
		  // JavaMailSender implementation needs to go here. 
	  } else {
		  return new ModelAndView("error-message");
	  }
	  
  }
  
  /**
   * Returns a list of users to return-user-list.jsp based on param.
   * @param request http servlet request
   */
  @RequestMapping(value = "/get-users", method = RequestMethod.GET)
  public ModelAndView getUser(HttpServletRequest request, 
      @RequestParam(value = "type", required = true) String type) {
    List<User> users;
    if (type == "buds") {
      users = userRepo.findByBuddyUpAndRole(1, roleRepo.findById(3));
    } else {
      users = (List<User>) userRepo.findAll();
    }
    return new ModelAndView("return-user-list","buds", users);
  }
  
  /**
   * View profile controller.  
   * @param pageno pagination of users
   * @return profile page objects 
   */
  @RequestMapping(value = "/view-profile", method = RequestMethod.GET)
  public ModelAndView viewProfile(
      @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageno) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ModelAndView profile = new ModelAndView("view-profile");
    User user = userRepo.findByLogin(auth.getName());
    
    List<BuddyUp> isInitialiser = buddyRepo.findByInitialiserId(user.getId());
    List<Profile> allProfiles = new ArrayList<Profile>();
    List<String> msgs = new ArrayList<String>();
    List<Notification> notif = new ArrayList<Notification>();
    
    int notifCount = notifyRepo.findByForUser(SecurityContextHolder.getContext().getAuthentication().getName()).size();
    profile.addObject("notifCount", notifCount);
        
    //Add buddyup requests to view
    List<Notification> allnot = ( List<Notification> ) notifyRepo.findAll();
    if (null == allnot) { 
      allnot = null;
    } else {
      for (Notification notice : allnot) {
        for (BuddyUp request : isInitialiser) {
          if (notice.getRequestId() == request.getId()) {
            notif.add(notice);
          }
        }
        if (notice.getForUser().equals(auth.getName())) {
          notif.add(notice);
        }
      }
      profile.addObject("NOTIF", notif);
    }
     
    //Get buddy profiles for searchers only
    List<User> buddies = userRepo.findByBuddyUpAndRole(1, roleRepo.findById(3));
    for (User n : buddies) {
      Profile prof = profileRepo.findByUserid(n.getId());
      if (n.getRole() == roleRepo.findById(3))  {
        allProfiles.add(prof);
      }
    }
    
    //Confirmed buddies
    List<BuddyUp> myBuddies = buddyRepo.findByInitialiserIdOrResponderId(
        user.getId(), user.getId());
    List<User> myBuddyList = new ArrayList<User>();
    User bud = new User();
    for ( BuddyUp b : myBuddies ) {
      if (user.getId() == b.getInitialiserId()) {
        bud = userRepo.findById(b.getResponderId());
      } else if (user.getId() == b.getResponderId()) {
        bud = userRepo.findById(b.getInitialiserId());
      }
      myBuddyList.add(bud);
      buddies.remove(bud);
    }
    
    
    //Pagination
    Integer indexStart = 1;
    Integer indexFinish = 10;
    if (buddies.size() < 10) {
      indexFinish = buddies.size();
    }
    if (pageno > 0) {
      indexStart = pageno * 10;
      indexFinish = indexStart + 10;
      if (indexFinish > buddies.size()) {
        indexFinish = buddies.size();
      }
    }
    
    //List of outstanding buddyup requests.
    List<BuddyUp> notConfirmed = buddyRepo.findByInitialiserIdOrResponderIdAndConfirmed(
        user.getId(), user.getId(), 0);
    
    //List properties user has expressed interest in.
    List<PropertyInterest> propint = propintRepo.findByUserId(user.getId());
    List<Property> propertyList = new ArrayList<Property>();
    if (null == propint) { propint = null; } else {
      for (PropertyInterest p : propint) {
        Property property = propRepo.findById(p.getPropertyId());
        propertyList.add(property);
      }
    }
    
    //List of feedback left by current user
    List<PropertyFeedback> feedback = feedRepo.findByUserName(auth.getName());
    List<Property> ratedProps = new ArrayList<Property>();
    for (PropertyFeedback p : feedback){
      Property rated = propRepo.findById(p.getPropertyId());
      ratedProps.add(rated);
    }
    
    //Find buddies with similar interests.
    List<Interest> myInterests = interRepo.findByUserId(user.getId());
    List<Interest> theirInterests = (List<Interest>) interRepo.findAll();
    List<User> matchedUsers = new ArrayList<User>();
    List<Profile> matchedProfiles = new ArrayList<Profile>();
    List<Interest> matchedInterests = new ArrayList<Interest>();
    if (null == myInterests || null == theirInterests) {} else {
      for (Interest i : myInterests){
        for (Interest t : theirInterests) {
          if (i.getInterest().equals(t.getInterest())){
            User u = userRepo.findById(t.getUserId());
            if (matchedUsers.contains(u)) {} else {
              matchedUsers.add(u);
            }
            matchedProfiles.add(profileRepo.findByUserid(u.getId()));
            matchedInterests.add(t);
          }
        }
      }
    }
    
    
    Profile myProfile = profileRepo.findByUserid(user.getId());
    
    //Getting this users history 
    List<UserPropertyHistory> userHistory = userPropertyHistoryRepo.findByUserId(user.getId());
    List<Property> userPropertyHistory = new ArrayList<Property>();
    
    if(userHistory != null){
    	for (UserPropertyHistory i : userHistory){
    		Property propertyHistory = propRepo.findById(i.getPropertyId());
    		userPropertyHistory.add(propertyHistory);
    	}
    }
    
    int pagecount = buddies.size() / 10;
    profile.addObject("RATEDPROPS", ratedProps);                                  //List of properties rated by current user
    profile.addObject("FEEDBACK", feedback);                                      //List of feedback left by current user
    profile.addObject("MATCHEDUSER", matchedUsers);                               //List of Users that match current user interests
    profile.addObject("MATCHEDPROFILE", matchedProfiles);                         //List of Profiles that match current user interests
    profile.addObject("MATCHEDINTEREST", matchedInterests);                       //List of Interests from other users that match current user interests
    profile.addObject("PROPERTY", propertyList);                                  //List of properties of which current user has expressed interest 
    profile.addObject("CURRENT", pageno);                                         //Current page number
    profile.addObject("MYBUDS", myBuddyList);                                     //List of buddyup request confirmed users 
    profile.addObject("ALLMYBUDS", myBuddies);                                    //List of confirmed buddyup requests
    profile.addObject("NOTCONFIRM", notConfirmed);                                //List of nonconfirmed buddyup requests
    profile.addObject("USER", user);                                              //Current logged in user
    profile.addObject("BUDS", buddies.subList(indexStart - 1, indexFinish - 1));  //List of Users with buddyup option enabled 
    profile.addObject("PROFILE", myProfile);                                      //Current users profile
    profile.addObject("PROFILELIST", allProfiles);                                //All available profiles for buddies
    profile.addObject("PAGES", pagecount);                                        //Number of pages of buddy user list
    profile.addObject("INTEREST", myInterests);                                   //Current users interests
    profile.addObject("USERHISTORY", userPropertyHistory);                        //The properties the user has visited
    return profile;
  }
  
  /**
   * Controller method for adding an interest.
   * @param addint interest to add
   * @return redirects to view-profile.jsp
   */
  @RequestMapping(value = "/add-interest", method = RequestMethod.GET)
  public String addInterest(
      @RequestParam(value = "addint", required = true) String addint){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Interest interest = new Interest();
    User user = userRepo.findByLogin(auth.getName());
    interest.setUserId(user.getId());
    interest.setInterest(addint);
    interRepo.save(interest);
    return "redirect:view-profile";
  }
  
  /**
   * Controler method to remove the received interest.
   * @param remint interest to remove
   * @return redirects to view-profile.jsp
   */
  @RequestMapping(value = "/remove-interest", method = RequestMethod.GET)
  public String removeInterest(
      @RequestParam(value = "remint", required = true) String remint){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepo.findByLogin(auth.getName());
    Interest interest = interRepo.findByInterestAndUserId(remint, user.getId());
    interRepo.delete(interest);
    return "redirect:view-profile";
  }
  
  /**
   * @return ModelAndView view profile
   */
  @RequestMapping(value = "/amend-user", method = RequestMethod.POST)
  public String amendUser(HttpServletRequest request,
      @RequestParam(value = "title", required = false, defaultValue = "x") String title,
      @RequestParam(value = "firstname", required = false, defaultValue = "x") String firstname,
      @RequestParam(value = "lastname", required = false, defaultValue = "x") String lastname,
      @RequestParam(value = "status", required = false) Integer status,
      @RequestParam(value = "address1", required = false, defaultValue = "x") String address1,
      @RequestParam(value = "address2", required = false, defaultValue = "x") String address2,
      @RequestParam(value = "address3", required = false, defaultValue = "x") String address3,
      @RequestParam(value = "postcode", required = false, defaultValue = "x") String postcode,
      @RequestParam(value = "email", required = false, defaultValue = "x") String email,
      @RequestParam(value = "p1", required = false, defaultValue = "x") String password,
      @RequestParam(value = "buddyup", required = false) Integer buddyup,
      @RequestParam(value = "username", required = false, defaultValue = "x") String username) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepo.findByLogin(auth.getName());
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();

    if (firstname.equals("x")) {} else { user.setFirstName(firstname); };
    if (lastname.equals("x")) {} else { user.setLastName(lastname); };
    if (status == null) {} else { user.setStatus(status); };
    if (address1.equals("x")) {} else { user.setAddress1(address1); }
    if (address2.equals("x")) {} else { user.setAddress2(address2); };
    if (address3.equals("x")) {} else { user.setAddress3(address3); };
    if (postcode.equals("x")) {} else { user.setPostcode(postcode); };
    if (email.equals("x")) {} else { user.setEmail(email); };
    if (password.equals("x")) {} else { user.setPassword(pe.encode(password)); };
    
    if (buddyup == null) {} else { user.setBuddyUp(buddyup); };
    userRepo.save(user);
    //return new ModelAndView("view-profile","USER",user);
    return "redirect:view-profile";
  }
  
  @RequestMapping(value="/admin-dashboard/users", method=RequestMethod.GET)
  public ModelAndView adminUsers(){
	  ModelAndView modelAndView = new ModelAndView("admin-users");
	  List<User> allUsers = (List<User>) userRepo.findAll();
	  modelAndView.addObject("userlist", allUsers);
	  return modelAndView;
  }
  
  @RequestMapping(value="/admin-dashboard/users/suspend", method=RequestMethod.GET)
  public String suspendUser(HttpServletRequest request,
		  @RequestParam(value="id", required=true) Integer id){
	  User user = userRepo.findById(id);
	  user.setOldRole(user.getRole().getId());
    user.setRole(roleRepo.findById(4));
	  user.setAccountStatus("suspended");
	  userRepo.save(user);
	  return "redirect:/admin-dashboard/users";
  }
  
  @RequestMapping(value="/admin-dashboard/users/unsuspend", method=RequestMethod.GET)
  public String unsuspendUser(HttpServletRequest request,
		  @RequestParam(value="id", required=true) Integer id ){
	  User user = userRepo.findById(id);
	  user.setRole(roleRepo.findById(user.getOldRole()));
	  user.setAccountStatus("active");
	  userRepo.save(user);
	  return "redirect:/admin-dashboard/users";
  }
  
  @RequestMapping(value="/admin-dashboard/users/delete", method=RequestMethod.GET)
  public String deleteUser(HttpServletRequest request,
  @RequestParam(value="id", required=true) Integer id ){
	  User user = userRepo.findById(id);
	  user.setPassword("can never login because when trying to login this is pe.encoded"); // just so the user can never login 
	  user.setAccountStatus("deleted");
	  userRepo.save(user);
	  return "redirect:/admin-dashboard/users";
  }
  
  @RequestMapping(value="/admin-dashboard/users/edit", method = RequestMethod.GET)
  public ModelAndView adminUserEdit(HttpServletRequest request, @RequestParam(value="id", required=true) Integer id){
	  ModelAndView modelAndView = new ModelAndView("adminUserAmend");
	  User user = userRepo.findById(id);
	  modelAndView.addObject("USER", user);
	  return modelAndView;
  }
  
  @RequestMapping(value = "/admin-dashboard/users/submitAdminEdit", method = RequestMethod.POST)
  public String adminUserEditSubmit(HttpServletRequest request,
      @RequestParam(value = "id", required=true) Integer id,
      @RequestParam(value = "title", required = false, defaultValue = "x") String title,
      @RequestParam(value = "firstname", required = false, defaultValue = "x") String firstname,
      @RequestParam(value = "lastname", required = false, defaultValue = "x") String lastname,
      @RequestParam(value = "status", required = false) Integer status,
      @RequestParam(value = "address1", required = false, defaultValue = "x") String address1,
      @RequestParam(value = "address2", required = false, defaultValue = "x") String address2,
      @RequestParam(value = "address3", required = false, defaultValue = "x") String address3,
      @RequestParam(value = "postcode", required = false, defaultValue = "x") String postcode,
      @RequestParam(value = "email", required = false, defaultValue = "x") String email,
      @RequestParam(value = "p1", required = false, defaultValue = "x") String password,
      @RequestParam(value = "buddyup", required = false) Integer buddyup,
      @RequestParam(value = "username", required = false, defaultValue = "x") String username) {
    User user = userRepo.findById(id);
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    if (title.equals("x")) {System.out.println("title");} else { user.setTitle(title); };
    if (firstname.equals("x")) {} else { user.setFirstName(firstname); };
    if (lastname.equals("x")) {} else { user.setLastName(lastname); };
    if (status == null) {} else { user.setStatus(status); };
    if (address1.equals("x")) {} else { user.setAddress1(address1); }
    if (address2.equals("x")) {} else { user.setAddress2(address2); };
    if (address3.equals("x")) {} else { user.setAddress3(address3); };
    if (postcode.equals("x")) {} else { user.setPostcode(postcode); };
    if (email.equals("x")) {} else { user.setEmail(email); };
    if (password.equals("x")) {} else { user.setPassword(pe.encode(password)); };
    if (buddyup == null) {} else { user.setBuddyUp(buddyup); };
    userRepo.save(user);
    return "redirect:/admin-dashboard/users";
  }
  
  @RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
  public ModelAndView forgotPasswordShow(){
	  return new ModelAndView("forgotPassPage");
  }
  
  @RequestMapping(value="/submitForgotPassword", method=RequestMethod.POST)
  public ModelAndView forgotPasswordSubmit(
		  @RequestParam(value="email", required=true) String email,
		  @RequestParam(value="secret", required=true) String secret,
		  @RequestParam(value="answer", required=true) String answer,
		  @RequestParam(value="p1", required=true) String p1,
		  @RequestParam(value="p2", required=true) String p2
		  ){
	  try{
		  User usr = userRepo.findByEmail(email);
		  if(usr.getAnswer().equals(answer)){
			  if(p1.equals(p2)){
				  BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
				  usr.setPassword(pe.encode(p1));
				  userRepo.save(usr);
				  return new ModelAndView("successReset").addObject("status", "Your password has been reset!");
			  } else {
				  return new ModelAndView("successReset").addObject("status", "There was a problem.");
			  }
		  } else {
			  return new ModelAndView("successReset").addObject("status", "There was a problem.");
		  }
	  } catch(Exception e){
		  return new ModelAndView("successReset").addObject("status", "There was a problem.");
	  }
  }
  
  /**
   * method return string for junit controller test.
   * @return string "account"
   */
  public String getTestMessage(){
    return "account";
  }
  
}