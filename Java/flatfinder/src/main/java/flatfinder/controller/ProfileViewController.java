package flatfinder.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.domain.BuddyUp;
import flatfinder.domain.Profile;
import flatfinder.domain.User;
import flatfinder.persistence.repository.BuddyUpRepository;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.ProfileRepository;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserRepository;

/**
 * Added this comment to test the continuous integration setup
 * is now working.
 * @author willp
 */
@Controller
public class ProfileViewController {
  
  @Autowired
  private BuddyUpRepository buddyRepo;
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private NotificationRepository notifyRepo;
  
  @Autowired
  private ProfileRepository profileRepo;
  
  @Autowired
  private RoleRepository roleRepo;
  
  /**
   * Returns user by submitted username.
   */
  @RequestMapping(value = "/view-buddy-profile", method = RequestMethod.GET)
  public ModelAndView returnProfile(HttpServletRequest request,
      @RequestParam(value = "username", required = true) String username) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ModelAndView userdets = new ModelAndView("return-buddy-profile");
    System.out.println("Getting user..");
    User user = userRepo.findByLogin(username);
    User loggedInUser = userRepo.findByLogin(auth.getName());
    Profile profile = profileRepo.findByUserid(user.getId());
    profile.setHits();
    profileRepo.save(profile);
    List<User> buddies = userRepo.findByBuddyUpAndRole(1, roleRepo.findById(3));
    List<BuddyUp> myBuddies = buddyRepo.findByInitialiserIdOrResponderIdAndConfirmed(user.getId(), user.getId(), 1);
    List<User> myBuddyList = new ArrayList<User>();
    User bud = new User();
    String isBuddy = "You can request to buddy up with " + user.getLogin();;
    for (BuddyUp b : myBuddies){
      if (user.getId() == b.getInitialiserId()) {
        bud = userRepo.findById(b.getResponderId());
      } else if (user.getId() == b.getResponderId()) {
        bud = userRepo.findById(b.getInitialiserId());
      }
      if (loggedInUser.getId() == b.getInitialiserId()) {
        if (b.getConfirmed() == 1) {
          isBuddy = "You and " + user.getLogin() + " are buddies.";
        } else if (b.getConfirmed() == 0) {
          isBuddy = "You have sent " + user.getLogin() + " a buddy request.";
        }
      } else if (loggedInUser.getId() == b.getResponderId()) {
        if (b.getConfirmed() == 1) {
          isBuddy = "You and " + user.getLogin() + " are buddies.";
        } else if (b.getConfirmed() == 0) {
          isBuddy = "You have sent " + user.getLogin() + " a buddy request.";
        }
      } 
      myBuddyList.add(bud);
      buddies.remove(bud);
    }
    if (user.getBuddyUp() == 1) {
      userdets.addObject("BUDDY", user);
      userdets.addObject("MYBUDS", myBuddyList);
      userdets.addObject("BUDS", buddies);
      userdets.addObject("ISBUD", isBuddy);
      userdets.addObject("PROFILE", profile);
    }
    return userdets;
  }
  
  @RequestMapping(value = "/amend-profile", method = RequestMethod.POST)
  public ModelAndView amendProfile(HttpServletRequest request,
      @RequestParam(value = "headline", required = false) String headline,
      @RequestParam(value = "about", required = false) String about,
      @RequestParam(value = "studying", required = false) String studying,
      @RequestParam(value = "workplace", required = false) String workplace,
      @RequestParam(value = "studyyear", required = false) Integer studyyear,
      @RequestParam(value = "quietorlively", required = false) Integer quietorlively,
      @RequestParam(value = "samesexormixed", required = false) Integer samesexormixed,
      @RequestParam(value = "smoking", required = false) Integer smoking,
      @RequestParam(value = "pets", required = false) Integer pets,
      @RequestParam(value = "ensuite", required = false) Integer ensuite,
      @RequestParam(value = "maxsharers", required = false) Integer maxsharers,
      @RequestParam(value = "facebookurl", required = false) String facebookurl,
      @RequestParam(value = "twitterurl", required = false) String twitterurl,
      @RequestParam(value = "googleplusurl", required = false) String googleplusurl,
      @RequestParam(value = "linkedinurl", required = false) String linkedinurl,
      @RequestParam(value = "studyplace", required = false) String studyplace,
      @RequestParam(value = "sp-postcode", required = false) String spostcode,
      @RequestParam(value = "wp-postcode", required = false) String wpostcode) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepo.findByLogin(auth.getName());
    Profile profile = profileRepo.findByUserid(user.getId());
    if (null == profile) {
      profile = new Profile();
      profile.setUserid(user.getId());
    }
    if (null == headline || headline.equals("")) {} else { profile.setHeadline(headline); };
    if (null == about || about.equals("")) {} else { profile.setAbout(about);};
    if (null == studying || studying.equals("")) {} else { profile.setStudying(studying); };
    if (null == workplace || workplace.equals("")) {} else { profile.setWorkplace(workplace); }
    if (null == studyyear) {} else { profile.setStudyyear(studyyear); };
    if (null == quietorlively) {} else { profile.setQuietorlively(quietorlively); };
    if (null == samesexormixed) {} else { profile.setSamesexormixed(samesexormixed); };
    if (null == smoking) {} else { profile.setSmoking(smoking); };
    if (null == pets) {} else { profile.setPets(pets); };
    if (null == ensuite) {} else { profile.setEnsuite(ensuite); };
    if (null == maxsharers) {} else { profile.setEnsuite(maxsharers); };
    if (null == facebookurl || facebookurl.equals("")) {} else { profile.setFacebookurl(facebookurl); };
    if (null == twitterurl || twitterurl.equals("")) {} else { profile.setTwitterurl(twitterurl); };
    if (null == googleplusurl || googleplusurl.equals("")) {} else { profile.setGoogleplusurl(googleplusurl); };
    if (null == linkedinurl || linkedinurl.equals("")) {} else { profile.setLinkedinurl(linkedinurl); };
    if (null == wpostcode || wpostcode.equals("")) {} else { profile.setWpostcode(wpostcode); };
    if (null == spostcode || spostcode.equals("")) {} else { profile.setSpostcode(spostcode); };
    if (null == studyplace || studyplace.equals("")) {} else { profile.setStudyplace(studyplace); };
    profile.setFirstEdit(1);
    profileRepo.save(profile);
    
    ModelAndView pv = new ModelAndView("view-profile");
    List<User> buddies = userRepo.findByBuddyUpAndRole(1, roleRepo.findById(3));
    List<BuddyUp> myBuddies = buddyRepo.findByInitialiserIdOrResponderIdAndConfirmed(user.getId(), user.getId(), 1);
    User bud = new User();
    List<User> myBuddyList = new ArrayList<User>();
    for (BuddyUp b : myBuddies){
      if (user.getId() == b.getInitialiserId()) {
        bud = userRepo.findById(b.getResponderId());
      } else if (user.getId() == b.getResponderId()) {
        bud = userRepo.findById(b.getInitialiserId());
      }
      myBuddyList.add(bud);
      buddies.remove(bud);
    } 
    
    Profile myProfile = profileRepo.findByUserid(user.getId());
    pv.addObject("MYBUDS", myBuddyList);
    pv.addObject("USER", user);
    pv.addObject("BUDS", buddies);
    pv.addObject("PROFILE", myProfile);
    return pv;
  }
  
  /**
   * method return string for junit controller test.
   * @return string "account"
   */
  public String getTestMessage(){
    return "profileview";
  }
  
}