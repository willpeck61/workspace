package flatfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.domain.Notification;
import flatfinder.persistence.repository.NotificationRepository;

@Controller
public class NotificationController {
	
	@Autowired
	private NotificationRepository notifRepo;
	
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public ModelAndView showNotifications(){
		ModelAndView modelAndView = new ModelAndView("/notifications");
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Notification> allNotifs = notifRepo.findByForUser(currentUsername);
		List<Notification> unreadNotifs = notifRepo.findByIsSeenAndForUser(false, currentUsername);
		int numUnreadNotifs = unreadNotifs.size();
		modelAndView.addObject("notifications", allNotifs);
		modelAndView.addObject("numUnreadNotifs", numUnreadNotifs);
		for(Notification n: allNotifs){
			n.setIsSeen(true);
			notifRepo.save(n);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/notificationcount", method=RequestMethod.GET)
	public ModelAndView getCount(){
		ModelAndView modelAndView = new ModelAndView("notificationcount");
		int count = notifRepo.findByIsSeenAndForUser(false, SecurityContextHolder.getContext().getAuthentication().getName()).size();
		modelAndView.addObject("count", count);
		return modelAndView;
	}
	
  /**
   * method return string for junit controller test.
   * @return string "session"
   */
  public String getTestMessage(){
    return "notification";
  }

}
