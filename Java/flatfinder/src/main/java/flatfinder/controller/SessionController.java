package flatfinder.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.FlatFinder;
import flatfinder.domain.Profile;
import flatfinder.domain.User;
import flatfinder.persistence.repository.ProfileRepository;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.UserRepository;



@Controller
public class SessionController {
  
  @Autowired
  private RoleRepository roleRepo;
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private ProfileRepository profRepo;
  
  /**
   * Returns landing page.
   * @return login-form.jsp
   */
  @RequestMapping(value = "/user-login", method = RequestMethod.GET)
  public ModelAndView loginForm() {
    
    //Load test users REMOVE for live deployment
    try {
      testUsers();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    //Check each user account has not expired and suspend if needed.
    List<User> currUsers = (List<User>) userRepo.findAll();
    Date date = new Date();
    for(User us : currUsers) {
      if (us.getRole().equals(us.getRole().equals(roleRepo.findById(3)) || 
          us.getRole().equals(roleRepo.findById(2)))) {
      Long expired = null;
      Long daysinms = TimeUnit.DAYS.toMillis(30); //30 days
      expired = date.getTime() - us.getLastActive().getTime();
        if (expired > daysinms) {
          us.setOldRole(us.getRole().getId());
          us.setRole(roleRepo.findById(4));
          userRepo.save(us);
        }
      }
    }
    return new ModelAndView("login-form");
  }
  
  /**
   * Populates test users.
   * @return login-form.jsp
   * @throws Exception FileNotFound
   */
  @RequestMapping(value = "/test-users", method = RequestMethod.GET)
  public ModelAndView testUsers() throws Exception {
      User user = new User();
      List<User> userlist = new ArrayList<User>();
      String startingUserDB     = "moreTestUserDB.txt";
      BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
      Scanner newUserFile = new Scanner(new File(startingUserDB));
      int count = 0;
      List<User> currUsers = (List<User>) userRepo.findAll();
      
      //Only load test users once
      if (currUsers.size() < 10) { 
        while(newUserFile.hasNextLine()){
          user = new User();
            newUserFile.nextLine();
          user.setRole(roleRepo.findById(newUserFile.nextInt()));
            newUserFile.nextLine(); //Needed as nextInt() excludes carriage returns
            newUserFile.nextLine();
          user.setLogin(newUserFile.nextLine());
          user.setPassword(pe.encode(newUserFile.nextLine()));
          user.setAddress1(newUserFile.nextLine());
          user.setAddress2(newUserFile.nextLine());
          user.setAddress3(newUserFile.nextLine());
          user.setPostcode(newUserFile.nextLine());
          user.setBuddyUp(Integer.valueOf(newUserFile.nextLine()));
          user.setAgeGroup(Integer.valueOf(newUserFile.nextLine()));
          user.setStatus(Integer.valueOf(newUserFile.nextLine()));
          user.setEmail(newUserFile.nextLine());
          user.setGender(Integer.valueOf(newUserFile.nextLine()));
            newUserFile.nextLine();
          user.setTitle(newUserFile.nextLine());
          user.setFirstName(newUserFile.nextLine());
          user.setLastName(newUserFile.nextLine());
          user.setDateCreated();
          user.setLastActive(null);
          userRepo.save(user);
          userlist.add(user);
        }
        newUserFile.close();
        for (User a : userlist) {
          new File(FlatFinder.ROOT + "/users/" + a.getLogin()).mkdirs();
          File sourceF = new File(FlatFinder.ROOT + "/img/female.png");
          File sourceM = new File(FlatFinder.ROOT + "/img/male.png");
          File dest = new File(FlatFinder.ROOT + "/users/" + a.getLogin() + "/" + a.getLogin() + "_profile.jpg");
          try {
            if (a.getGender() == 0) {
              FileCopyUtils.copy(sourceF, dest);
            } else {
              FileCopyUtils.copy(sourceM, dest);
            }
          } catch (IOException e) {
              e.printStackTrace();
          }
        }
        userlist = (List<User>) userRepo.findAll();
        for (User i : userlist){
          Profile profile = new Profile();
          profile.setUserid(i.getId());
          profile.setFirstEdit(0);
          Profile verif = profRepo.findByUserid(i.getId());
          if (null == verif) {
            profRepo.save(profile);
          }
        }
      }
    return new ModelAndView("login-form");
  }
  /**
   * Redirects http requests from /user-registration url to
   * the returned view register-form.jsp
   * @return view register-form.jsp
   **/
  @RequestMapping(value = "/user-registration", method = RequestMethod.GET)
  public ModelAndView userReg() {
    return new ModelAndView("register-form");
  }
  
  /**
   * Redirects http requests from /error-login url to
   * the returned view login-form.jsp
   * @return view login-form.jsp
   **/
  @RequestMapping(value = "/error-login", method = RequestMethod.GET)
  public ModelAndView invalidLogin() {
    ModelAndView modelAndView = new ModelAndView("login-form");
    modelAndView.addObject("error", true);
    return modelAndView;
  }
  
  /**
   * If /success-login url then display new view main console
   * page. 
   * @param request stores http request data
   * @return "console-view.jsp"
   **/
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView successLogin(HttpServletRequest request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User us = userRepo.findByLogin(auth.getName());
    us.setLastActive(null);
    
    ModelAndView modview = new ModelAndView("index");
    modview.addObject("login", us.getLogin());
    return modview;
  }
  
  /**
   * User logs out "/user-logout" url.  
   * @return login-form.jsp
   **/
  @RequestMapping(value = "/user-logout", method = RequestMethod.GET)
  public ModelAndView logout() {
    ModelAndView modelAndView = new ModelAndView("/login-form");
    modelAndView.addObject("logout", true);
    return modelAndView;
  }
  
  /**
   * Return privacy policy page
   * @return privacy-policy.jsp
   */
  @RequestMapping(value = "/privacy", method = RequestMethod.GET)
  public ModelAndView privacy() {
    ModelAndView modelAndView = new ModelAndView("privacy-policy");
    return modelAndView;
  }
  
  /**
   * Returns terms and conditions page
   * @return terms-and-conditions.jsp
   */
  @RequestMapping(value = "/terms", method = RequestMethod.GET)
  public ModelAndView terms() {
    ModelAndView modelAndView = new ModelAndView("terms-and-conditions");
    return modelAndView;
  }
  
  /**
   * User makes boo-boo triggering /user-error url.
   * @return map to /error-message url
   **/
  @RequestMapping(value = "/user-error", method = RequestMethod.GET)
    public String error() {
    return "/error-message";
  }
  
  
  /**
   * Method returns string for junit controller test.
   * @return string "session"
   */
  public String getTestMessage(){
    return "session";
  }
}
