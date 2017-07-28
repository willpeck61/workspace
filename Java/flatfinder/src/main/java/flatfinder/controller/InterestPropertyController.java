package flatfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import flatfinder.domain.Notification;
import flatfinder.domain.Property;
import flatfinder.domain.PropertyInterest;
import flatfinder.domain.User;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.PropertyInterestRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class InterestPropertyController {
	
		@Autowired
		private PropertyRepository propertyRepo;
		@Autowired
		private UserRepository userRepo;
		@Autowired
		private NotificationRepository notifRepo;
		@Autowired
		private PropertyInterestRepository propertyInterestRepo;
	
		@RequestMapping(value="/InterestedProperty", method = RequestMethod.GET)
		public String interestedProperty(
			@RequestParam(value = "id", required = true) Integer id,
		  @RequestParam(value = "username", required = true) String username) {
		
		Integer currentId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
		
		Property property = propertyRepo.findById(id);
	    property.setNumViews(property.getNumViews() + 1);
	  	
    	Notification newNoti = new Notification();
    	PropertyInterest propInterest = new PropertyInterest();
    	
    	int propOwner = property.getCreatedBy();
    	
    	property.setNumInterests(property.getNumInterests()+1);
    	
    	//Getting the username of a user by their id
    	String userUsername = userRepo.findById(propOwner).getLogin();
    	
    	//Getting the userName of the person who is interested
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByLogin(auth.getName());

    	newNoti.setForUser(userUsername);
    	newNoti.setNotificationText(user.getLogin() + " is interested in " + property.getPostcode());
    	newNoti.setWhenCreated("today");
    	newNoti.setNotificationType("Expressed Interest");
    	newNoti.setPropertyPostcode(property.getPostcode());
    	newNoti.setCreatedBy(username);
    	newNoti.setRedirUrl("/view-buddy-profile?username=" + username);
    	
    	propInterest.setPropertyId(id);
    	propInterest.setUserId(currentId);
    	
    	propertyInterestRepo.save(propInterest);
	    notifRepo.save(newNoti);
	    propertyRepo.save(property);
		
		return "redirect:/property?id="+id;
		
	}
		
		@RequestMapping(value="/removeInterestedProperty", method = RequestMethod.GET)
		public String removeInterestedProperty(
			@RequestParam(value = "id", required = true) Integer id){
			
			Integer currentId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();			
			List<PropertyInterest> currentInterest = propertyInterestRepo.findByUserIdAndPropertyId(currentId, id);
			propertyInterestRepo.delete(currentInterest.get(0));
			
			Property property = propertyRepo.findById(id);
			Notification newNoti = notifRepo.findByPropertyPostcode(property.getPostcode());
			notifRepo.delete(newNoti);
			
			return "redirect:/property?id="+id;
		}
		
  /**
   * method return string for junit controller test.
   * @return string "Interest"
   */
  public String getTestMessage(){
    return "interest";
  }
}
