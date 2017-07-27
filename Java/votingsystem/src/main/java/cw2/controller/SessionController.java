package cw2.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cw2.domain.Option;
import cw2.domain.Question;
import cw2.domain.SecurityCode;
import cw2.domain.User;
import cw2.domain.Vote;
import cw2.persistence.repository.OptionRepository;
import cw2.persistence.repository.QuestionRepository;
import cw2.persistence.repository.SecurecodeRepository;
import cw2.persistence.repository.UserRepository;
import cw2.persistence.repository.VoteRepository;

@Controller
public class SessionController {
  
  @Autowired
  UserRepository userRepo;
  
  @Autowired
  SecurecodeRepository secRepo;
  
  @Autowired
  QuestionRepository questRepo;
  
  @Autowired
  VoteRepository voteRepo;
  
  @Autowired
  OptionRepository optRepo;
  
  @RequestMapping(value = "/", 
      method = RequestMethod.GET)
  public ModelAndView landingPage(
      @CookieValue(value = "lastUser", defaultValue = "") String lastuser,
      HttpServletResponse response, HttpServletRequest request) {
    
    //Cookie[] cookies = request.getCookies();
    response.addCookie(new Cookie("lastUser","world"));
    ModelAndView page = new ModelAndView("index");
    return page;
  }
  
  @RequestMapping(value = "/user-logout", 
      method = RequestMethod.GET)
  public ModelAndView logoutPage() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    auth.setAuthenticated(false);
    ModelAndView page = new ModelAndView("index");
    return page;
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/add-question",
      method = RequestMethod.POST)
  public @ResponseBody String addQuestion(
      @RequestParam(value = "question", required = true) String question,
      @RequestParam(value = "date", required = true) 
        @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
      @RequestParam(value = "option1", required = true) String option1,
      @RequestParam(value = "option2", required = true) String option2,
      @RequestParam(value = "option3", required = false) String option3,
      @RequestParam(value = "option4", required = false) String option4,
      @RequestParam(value = "option5", required = false) String option5) {
    Question quest = new Question();
    quest.setQuestion(question);
    quest.setExpires(date);
    questRepo.save(quest);
    
    List<String> options = new ArrayList<>();
    options.add(option1);
    options.add(option2);
    if (!("".equals(option3))) {options.add(option3);}
    if (!("".equals(option4))) {options.add(option4);}
    if (!("".equals(option5))) {options.add(option5);}
    for (String s : options) {
      Option option = new Option();
      option.setOption(s);
      option.setQuestion(quest);
      optRepo.save(option);
    }
    List<Question> questlist = (List<Question>) questRepo.findAll();
    JSONArray jsonarray = new JSONArray();
    for (Question q : questlist) {
      JSONObject jq = new JSONObject();
      jq.put("question", q.getQuestion());
      jq.put("id", q.getId());
      jsonarray.add(jq);
    }
    JSONObject json = new JSONObject();
    json.put("questions", jsonarray);
    return json.toString(); 
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/add-securecode",
    method = RequestMethod.POST)
  public @ResponseBody String addSecurecode(
      @RequestParam(value = "code", required = true) final JSONObject codes,
      @RequestParam(value = "questionid", required = true) Integer id) {
    JSONArray codelist = (JSONArray) codes.get("codes");
    Iterator<String> i = codelist.iterator();
    List<String> dups = new ArrayList<>();
    JSONObject valcodes = new JSONObject();
    while (i.hasNext()) {
      SecurityCode cd = new SecurityCode();
      if (null == secRepo.findBySecuritycode(i.next())) {
        cd.setQuestion(questRepo.findById(id));
        cd.setSecuritycode(i.next());
        secRepo.save(cd);
        valcodes.put("code", 
            secRepo.findBySecuritycode(i.next()).getSecuritycode());
      } else {
        dups.add(i.next());
      }
    }
    JSONObject dupcodes = new JSONObject();
    if (!(null == dups)) {
      for (String d : dups) {
        dupcodes.put("code", d);
      }
    }
    JSONObject returncodes = new JSONObject();
    returncodes.put("valid",valcodes);
    returncodes.put("duplicates", dupcodes);
    return returncodes.toString();
  }
  
  @RequestMapping(value = "/modify-question",
      method = RequestMethod.POST)
  public @ResponseBody String modifyQuestion(
      @RequestParam(value = "questionid", required = true) Integer id,
      @RequestParam(value = "qtext", required = true) String text,
      @RequestParam(value = "qexpire", required = false) Date date,
      @RequestParam(value = "qdelete", required = false) Boolean del) {
    Question question = questRepo.findById(id);
    if (!(null == date)) {question.setExpires(date);}
    if (!(null == text)) {question.setQuestion(text);}
    questRepo.save(question);
    if (true == del) {questRepo.delete(id);}
    return "this";
  }
  @RequestMapping(value = "/registration", 
      method = RequestMethod.GET)
  public ModelAndView registerUser(HttpServletRequest request) {
    ModelAndView page = new ModelAndView("registration");
    return page;
  }
  
  @RequestMapping(value = "/place-vote",
      method = RequestMethod.POST)
  public @ResponseBody String placeVote(
      @RequestParam(value = "questionid", required = true) Integer id,
      @RequestParam(value = "optionid", required = true) Integer optionid,
      @RequestParam(value = "change", required = true) Boolean change) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Question question = questRepo.findById(id);
    User user = userRepo.findByUsername(auth.getName());
    Vote vote = new Vote();
    Option option = optRepo.findById(optionid);
    vote.setOption(option);
    vote.setUser(user);
    vote.setQuestion(question);
    if (null == voteRepo.findByUserAndQuestion(user, question)) {
      voteRepo.save(vote);
      return "Vote Successfully Recorded.";
    } else {
      if (!(change)){
        return "You have already voted. You can change your vote below.";
      } else {
        vote = voteRepo.findByUserAndQuestion(user, question);
        vote.setUser(user);
        vote.setQuestion(question);
        vote.setOption(option);
        voteRepo.save(vote);
        return "Your vote has been changed.";
      }
    }
  }
       
  @RequestMapping(value = "/process-login", 
      method = RequestMethod.GET)
  public ModelAndView processLogin(HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Date today = Calendar.getInstance().getTime();
    User user = userRepo.findByUsername(auth.getName());
    ModelAndView page;
    List<Question> questions = (List<Question>) questRepo.findByExpiresGreaterThanEqual(today);
    List<Vote> votes = voteRepo.findByUser(user);
    if (user.getRole() == 1) {
      page = new ModelAndView("officer-page");
    } else if (user.getRole() == 2){
      page = new ModelAndView("voter-page");
    } else {
      page = new ModelAndView("error-login");
    }
    response.addCookie(new Cookie("lastUser", user.getUsername()));
    page.addObject("questions", questions);
    page.addObject("user", user);
    page.addObject("votes", votes);
    return page;
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/return-options",
      method = RequestMethod.POST)
  public @ResponseBody String returnOptions(
      @RequestParam(value = "questionid", required = true) Integer id) {
    JSONObject json = new JSONObject();
    Question question = questRepo.findById(id);
    List<Option> options = optRepo.findByQuestion(question);
    for (Option o : options) {
      json.put(o.getId(), o.getOption());
    }
    return json.toString();
  }
  
  @RequestMapping(value = "/display-results", 
      method = RequestMethod.GET)
  public ModelAndView displayResults(
      @RequestParam(value = "questionid", required = true) Integer id) {
    Question question = questRepo.findById(id);
    return new ModelAndView("display-results", "question", question);
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/check-votes",
      method = RequestMethod.POST)
  public @ResponseBody String checkVotes(
      @RequestParam(value = "questionid", required = true) Integer id) {
    JSONObject votelist = new JSONObject();
    Question question = questRepo.findById(id);
    List<Vote> votes = voteRepo.findByQuestion(question);
    if (!(votes.size() == 0)) {
      List<Option> options = (List<Option>) optRepo.findByQuestion(question);
      String opt = null;
      for (int k = 0; k < options.size(); k++) {
        int countvote = 0;
        for (Vote v : votes) {
          if (options.get(k).equals(v.getOption())) {
            countvote ++;
            opt = options.get(k).getOption();
            System.out.println(opt + " " + countvote);
          }
        }
        if (countvote == 0) {
          votelist.put(options.get(k).getOption(),0);
        } else {
          votelist.put(opt, countvote);
        }
      }
      return votelist.toString();
    } else {
      return "No votes have been cast.";
    }
    
  }
  
  @RequestMapping(value = "/error-login", 
      method = RequestMethod.GET)
  public ModelAndView errorLogin() {
    ModelAndView page = new ModelAndView("error-login");
    return page;
  }
  
  @RequestMapping(value = "/validate-code",
      method = RequestMethod.POST)
  public @ResponseBody String validateCode(HttpServletRequest request,
      @RequestParam(value = "code", required = true) String code) {
    String message = null;
    SecurityCode sc = secRepo.findBySecuritycode(code);
    if (null != sc && sc.getInuse() == false) {
      User user = new User();
      user.setUsername(request.getSession().getId());
      user.setActive(false);
      userRepo.save(user);
      sc.setInuse(true);
      sc.setUser(user);
      secRepo.save(sc);
      message = "<p id=\"valid\">Your security code is valid.</p>";
    } else if (null == sc) {
      message = "<p id=\"invalid\">Your code is not valid.</p>";
    } else if (null != sc.getUser() || sc.getInuse() == false){
      message = "<p id=\"invalid\">The provided security code has "
          + "already been used by another voter.</p>";
    }
    return message;
  }
  
  @RequestMapping(value = "/process-registration", 
      method = RequestMethod.POST)
  public @ResponseBody String processRegistration(HttpServletRequest request,
      @RequestParam(value = "username", required = true) String username,
      @RequestParam(value = "password", required = true) String password,
      @RequestParam(value = "title", required = true) String title,
      @RequestParam(value = "firstname", required = true) String firstname,
      @RequestParam(value = "lastname", required = true) String lastname,
      @RequestParam(value = "address1", required = true) String address1,
      @RequestParam(value = "address2", required = true) String address2,
      @RequestParam(value = "address3", required = true) String address3,
      @RequestParam(value = "postcode", required = true) String postcode,
      @RequestParam(value = "email", required = true) String email) {
    BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
    String data = null;
    User user = userRepo.findByUsername(request.getSession().getId());
    User validate = userRepo.findByUsername(username);
    User chkemail = userRepo.findByEmail(email);
    if (!(null == validate)) {
      data = "Username already exists. "
          + "Please choose another.";
    } else if (null == user) {
      data = "Security Violation - "
          + "You have not entered a valid code.";
    } else if (!(null == chkemail)) {
      data = "The email address entered is associated with another account.";
    } else {
      user.setUsername(username);
      user.setPassword(pe.encode(password));
      user.setTitle(title);
      user.setFirstname(firstname);
      user.setLastname(lastname);
      user.setAddress1(address1);
      user.setAddress2(address2);
      user.setAddress3(address3);
      user.setPostcode(postcode);
      user.setEmail(email);
      user.setActive(true);
      user.setRole(2);
      userRepo.save(user);
      data = "You have been registered "
          + "successfully.";
    }
    return data;
  }
}
