package flatfinder.controller;

import flatfinder.FlatFinder;
import flatfinder.domain.BuddyUp;
import flatfinder.domain.Interest;
import flatfinder.domain.Role;
import flatfinder.domain.User;
import flatfinder.domain.Notification;
import flatfinder.domain.Profile;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserRepository;
import flatfinder.persistence.repository.BuddyUpRepository;
import flatfinder.persistence.repository.InterestRepository;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.ProfileRepository;

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
public class BuddyController {
  
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
  private InterestRepository interRepo;
  
  /**
   * Returns user by submitted username.
   */
  @RequestMapping(value = "/buddy-search", method = RequestMethod.GET)
  public ModelAndView returnUser(HttpServletRequest request,
      @RequestParam(value = "querystr", required = false, defaultValue = "") String querystr) {
    List<User> users = null;
    List<Profile> profiles = null;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ModelAndView userdets = new ModelAndView("return-buddy"); 
    System.out.println("Getting user..");
    int flag = 0;
    User user = userRepo.findByLogin(querystr);
    if (querystr.equals("")){}
    else {
      if (null == user) {} else {
        if (user.getLogin().equals(auth.getName())){}
        else {
          if (user.getBuddyUp() == 1){
            Profile profile = profileRepo.findByUserid(user.getId());
            userdets.addObject("BUDDY", user);
            userdets.addObject("PROFILE", profile);
            flag = 1;
          }
        }
      }
      if (flag == 0) {
        List<Interest> inter = interRepo.findByInterest(querystr);
        if (null == inter) {} else {
          users = new ArrayList<User>();
          profiles = new ArrayList<Profile>();
          for (Interest i : inter) {
            user = userRepo.findById(i.getUserId());
            Profile profile = profileRepo.findById(i.getUserId());
            if (user.getLogin().equals(auth.getName())){}
            else {
              if (user.getBuddyUp() == 1){
                profiles.add(profile);
                users.add(user);
                flag = 1;
              }
            }
          }
        }
      }
      if (flag == 0) {
        users = userRepo.findByFirstNameOrLastName(querystr, querystr);
        if (null == users) {} else {
          profiles = new ArrayList<Profile>();
          for (User i : users) {
            Profile profile = profileRepo.findByUserid(i.getId());
            profiles.add(profile);
            users.add(i);
            flag = 1;
          }
        }
      }
      if (flag == 0) {
        profiles = profileRepo.findByStudyingOrWorkplace(querystr, querystr);
        if (null == profiles) {} else {  
          users = new ArrayList<User>();
          for (Profile i : profiles) {
            User usr = userRepo.findById(i.getUserid());
            profiles.add(i);
            users.add(usr);
            flag = 1;
          }
        }
      }
    }
    userdets.addObject("BUDDIES", users);
    userdets.addObject("PROFILES", profiles);
    return userdets;
  }
  
  /**
   * Make a buddy request
   */
  @RequestMapping(value = "/buddy-request", method = RequestMethod.GET)
  public ModelAndView buddyRequest(HttpServletRequest request,
      @RequestParam(value = "responder", required = true) String resp) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ModelAndView message = new ModelAndView("view-profile");
    User initialiser = userRepo.findByLogin(auth.getName());
    User responder = userRepo.findByLogin(resp);
    Boolean userChk = (null == initialiser || null == responder); 
    BuddyUp existRequest = null;
    if (userChk == true) {
      existRequest = null;
    } else {
      existRequest = buddyRepo.findByInitialiserIdAndResponderId(initialiser.getId(), responder.getId());
    }
    if (null == existRequest && userChk == false) {
      System.out.println("Sending buddy request..");
      BuddyUp buddyup = new BuddyUp();
      buddyup.setInitialiserId(initialiser.getId());
      buddyup.setResponderId(responder.getId());
      buddyup.setConfirmed(0);
      buddyRepo.save(buddyup);
      buddyup = buddyRepo.findByInitialiserIdAndResponderId(initialiser.getId(), responder.getId());
      String result = "Buddy request successfully sent to " + responder;
      message.addObject("result", result);
      Notification notification = new Notification();
      notification.setWhenCreated(new Date().toString());
      notification.setForUser(resp);
      notification.setNotificationText("New buddy request from " + initialiser.getLogin());
      notification.setNotificationType("BuddyUp");
      notification.setRedirUrl("view-buddy-request");
      notification.setRequestId(buddyup.getId());
      notifyRepo.save(notification);
      List<Notification> allnot = (List<Notification>) notifyRepo.findAll();
      List<BuddyUp> initReq = buddyRepo.findByInitialiserId(responder.getId());
      List<Notification> notif = new ArrayList<Notification>();
      for (Notification n : allnot){
        for (BuddyUp b : initReq) {
          if (n.getRequestId() == b.getId()) {
            notif.add(n);
          }
        }
      }
      message.addObject("NOTIF", notif);
    } else {
      String dupbud = "true";
      message.addObject("DUPBUD", dupbud);
    }
    return message;
  }
 
  /**
   * Remove buddy
   */
  @RequestMapping(value = "/remove-buddy", method = RequestMethod.GET)
  public String removeBuddy(HttpServletRequest request,
      @RequestParam(value = "username", required = true) String username,
      @RequestParam(value = "notifyid", required = false) Integer notid){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    ModelAndView userdets = new ModelAndView("return-buddy"); 
    System.out.println("Getting user..");
    if (null != notid) {
      notifyRepo.delete(notid);
    }
    User user = userRepo.findByLogin(username);
    BuddyUp bud = buddyRepo.findByInitialiserIdAndResponderId(userRepo.findByLogin(auth.getName()).getId(), user.getId());
    if (null == bud) {
      bud = buddyRepo.findByInitialiserIdAndResponderId(user.getId(), userRepo.findByLogin(auth.getName()).getId());
    }
    buddyRepo.delete(bud);
    return "redirect:/view-profile";
  }
  
  /**
   * Accept or Reject buddy request
   */
  @RequestMapping(value = "/action-buddy-request", method = RequestMethod.GET)
  public String actionBuddyRequest(HttpServletResponse response,
      @RequestParam(value = "notifyid", required = true) Integer notid,
      @RequestParam(value = "requestid", required = true) Integer reqid,
      @RequestParam(value = "response", required = true) String resp) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("Accepting buddy request..");
    User responder = userRepo.findByLogin(auth.getName());
    BuddyUp buddyup = buddyRepo.findById(reqid);
    User initialiser = userRepo.findById(buddyup.getInitialiserId());
    Notification notify = notifyRepo.findById(notid);
    if (null == notify) { 
      return "redirect:/notifications";
    } else {
      if ((notify.getRequestId() == reqid) && (notify.getForUser().equals(auth.getName()))) {
        if (resp.equals("reject")) {
          buddyRepo.delete(reqid);
          notifyRepo.delete(notid);
          System.out.println("Rejected Buddy Request..");
        } else if (resp.equals("accept")) {
          buddyup.setConfirmed(1);
          buddyRepo.save(buddyup);
          notifyRepo.delete(notid);
          System.out.println("Accepted Buddy Request..");
        }
      } else {
        System.out.println("INTEGRITY ERROR: Data Mismatch");
      }
    }
    return "redirect:/notifications";
  }
  
  /**
   * View buddy request
   */
  @RequestMapping(value = "/view-buddy-request", method = RequestMethod.GET)
  public ModelAndView viewBuddyRequest (HttpServletRequest request,
      @RequestParam(value = "notifyid", required = true) Integer notid) {
    System.out.println("Viewing buddy request..");
    ModelAndView buddyrequest = new ModelAndView("buddy-request");
    Notification notify = notifyRepo.findById(notid);
    if (null != notify) {
      BuddyUp buddyup = buddyRepo.findById(notify.getRequestId());
      try {
        User buddy = userRepo.findById(buddyup.getInitialiserId());
        Profile profile = profileRepo.findByUserid(buddy.getId());
        buddyrequest.addObject("BUDDY", buddy);
        buddyrequest.addObject("BUDDYUP", buddyup);
        buddyrequest.addObject("NOTIFY", notify);
        buddyrequest.addObject("PROFILE", profile);
      } catch (Exception e) {
        String requestCancelled = "The request was cancelled and no longer exists.";
        List<String> msgs = new ArrayList<String>();
        msgs.add(requestCancelled);
        notifyRepo.delete(notify);
        buddyrequest.addObject("MSG", msgs);
        buddyrequest = new ModelAndView("notifications");
      }
    }
    return buddyrequest;
  }
  
   
  /**
   * method return string for junit controller test.
   * @return string "account"
   */
  public String getTestMessage(){
    return "buddy";
  }
  
}