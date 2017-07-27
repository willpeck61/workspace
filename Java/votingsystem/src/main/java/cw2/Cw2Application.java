package cw2;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

@SpringBootApplication
public class Cw2Application implements ApplicationRunner {
  
  public static final int ROLE_ERO = 1;
  public static final int ROLE_VOTER = 2;
  
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
  
	public static void main(String[] args) {
		SpringApplication.run(Cw2Application.class, args);
	}

  @Override
  public void run(ApplicationArguments args) throws Exception {
    
    User user = new User();
    BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
    user.setUsername("ero");
    user.setActive(true);
    user.setPassword(pe.encode("password"));
    user.setRole(1);
    userRepo.save(user);
    
    User user2 = new User();
    user2.setUsername("test1");
    user2.setActive(true);
    user2.setPassword(pe.encode("password"));
    user2.setRole(2);
    userRepo.save(user2);
    
    User user3 = new User();
    user3.setUsername("test2");
    user3.setActive(true);
    user3.setPassword(pe.encode("password"));
    user3.setRole(2);
    userRepo.save(user3);
    
    User user4 = new User();
    user4.setUsername("test3");
    user4.setActive(true);
    user4.setPassword(pe.encode("password"));
    user4.setRole(2);
    userRepo.save(user4);
    
    Date expires = new GregorianCalendar(2016, 11, 25).getTime();
    Question question = new Question();
    question.setQuestion("Are you a test?");
    question.setActive(true);
    question.setExpires(expires);
    questRepo.save(question);
    
    Option option = new Option();
    option.setOption("Yes");
    option.setQuestion(question);
    optRepo.save(option);
    
    Option option2 = new Option();
    option2.setOption("No");
    option2.setQuestion(question);
    optRepo.save(option2);
    
    Option option3 = new Option();
    option3.setOption("Maybe");
    option3.setQuestion(question);
    optRepo.save(option3);
    
    String[] code = {
        "LRYUMOTX","KDFCDIGK","LNCSSCZM",
        "IOQNTMRW","RUVUYMUJ","LOIRWGLM",
        "NKIGTJUJ","RLLXYGEY","MJLIMMLZ",
        "TSHETHQB"
    };
    
    for (String str : code) {
      SecurityCode seccode = new SecurityCode();
      System.out.println("Added " + str);
      seccode.setSecuritycode(str);
      seccode.setQuestion(question);
      seccode.setUser(null);
      seccode.setInuse(false);
      secRepo.save(seccode);
    }
    
    Vote vote = new Vote();
    vote.setOption(option);
    vote.setUser(user);
    vote.setQuestion(question);
    voteRepo.save(vote);
    
    Vote vote2 = new Vote();
    vote2.setOption(option2);
    vote2.setUser(user2);
    vote2.setQuestion(question);
    voteRepo.save(vote2);
    
    Vote vote3 = new Vote();
    vote3.setOption(option3);
    vote3.setUser(user3);
    vote3.setQuestion(question);
    voteRepo.save(vote3);
    
    Vote vote4 = new Vote();
    vote4.setOption(option3);
    vote4.setUser(user4);
    vote4.setQuestion(question);
    voteRepo.save(vote4);
    
    
  }
}
