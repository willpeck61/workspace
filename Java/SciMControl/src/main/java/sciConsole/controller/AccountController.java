package sciConsole.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sciConsole.domain.User;
import sciConsole.persistence.repository.RoleRepository;
import sciConsole.persistence.repository.UserRepository;

@Controller
public class AccountController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
    
    @RequestMapping(value="/addUser", method=RequestMethod.GET)
    public String addUser(HttpServletRequest request,@RequestParam(value="user", required=true)String username,@RequestParam(value="password", required=true) String password,@RequestParam(value="role", required=true) String rol){
    	final int ROLE_ADMIN = 1;
        final int ROLE_USER = 2;
        BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    	System.out.println("Adding new user...");
        User user = new User();
       
        user.setLogin(username);
        user.setPassword(pe.encode(password));        
        if (rol.equals("DEVELOPER")){
            user.setRole(roleRepo.findById(ROLE_USER));
        } else if (rol.equals("MANAGER")){
            user.setRole(roleRepo.findById(ROLE_ADMIN));
        }
        
        userRepo.save(user);
        System.out.println("Added new user.");
		return "User: "+ username +" added";
    }
    
    @RequestMapping(value="/get-users", method=RequestMethod.GET)
    public ModelAndView getUser(HttpServletRequest request, HttpServletResponse response){
    		List<User> users = (List<User>) userRepo.findAll();
			return new ModelAndView("return-user-list","users", users);
    }
    
    @RequestMapping(value="/delete-user", method=RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request,@RequestParam(value="userid", required=true) String userid){
    	userRepo.delete(Integer.parseInt(userid));
    	List<User> users = (List<User>) userRepo.findAll();
    	for (User d : users){
    		if(d.getId() == Integer.parseInt(userid)){
    			users.remove(d);
    		}
    	}
    	userRepo.save(users);
    	return new ModelAndView("return-user-list","users",users);
	}
    
}

