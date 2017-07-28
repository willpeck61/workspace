package sciConsole.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sciConsole.persistence.repository.SciRepository;
import sciConsole.persistence.repository.VersionRepository;

@Controller
@RequestMapping("/")
public class SessionController {
	
	@Autowired
	private SciRepository sciRepo;
	
	@Autowired
	private VersionRepository verRepo;

	@RequestMapping(value="/user-login", method=RequestMethod.GET)
    public ModelAndView loginForm() {
        return new ModelAndView("login-form");
    }
    
    
    @RequestMapping(value="/error-login", method=RequestMethod.GET)
    public ModelAndView invalidLogin() {
        ModelAndView modelAndView = new ModelAndView("login-form");
        modelAndView.addObject("error", true);
        return modelAndView;
    }

    @RequestMapping(value="/success-login", method=RequestMethod.GET)
    public ModelAndView successLogin(HttpServletRequest request) {
    	ModelAndView modview = new ModelAndView("console-view");
    	modview.addObject("scis",sciRepo.findAll());
    	modview.addObject("versions",verRepo.findAll());
    	
        return modview;
    }
    
    @RequestMapping(value="/user-logout", method=RequestMethod.GET)
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView("login-form");
        modelAndView.addObject("logout", true);
        return modelAndView;
    }
    
    @RequestMapping(value="/user-error", method=RequestMethod.GET)
    public String error() {
        return "/error-message";
    }
}
