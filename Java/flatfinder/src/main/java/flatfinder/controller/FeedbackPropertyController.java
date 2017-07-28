package flatfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import flatfinder.domain.Notification;
import flatfinder.domain.Property;
import flatfinder.domain.PropertyFeedback;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.PropertyFeedbackRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class FeedbackPropertyController {
	
	@Autowired
	private PropertyRepository propertyRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private NotificationRepository notifRepo;
	@Autowired
	private PropertyFeedbackRepository feedbackRepo;
	
	@RequestMapping(value="/submitFeedback", method = RequestMethod.POST)
	public String submitFeedback(
			@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="rating", required=true) String rating,
			@RequestParam(value="comment", required=true) String comment
			){
		
		Property prop = propertyRepo.findById(id);
		if(!prop.getListed()){
			return "redirect:/error-message";
		} else {
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			boolean exists = false;
			for(PropertyFeedback f:feedbackRepo.findByPropertyId(id)){
				if(f.getUserName().equals(user)){
					exists = true;
				}
			}
			if(exists){
				return "redirect:/property?id="+id;
			} else {
				PropertyFeedback feedback = new PropertyFeedback();
				feedback.setPropertyId(id);
				feedback.setCreated("now");
				feedback.setRating(Integer.parseInt(rating));
				feedback.setUserName(user);
				feedback.setApproved(false);
				feedback.setComment(comment);
				feedbackRepo.save(feedback);
				Notification notification = new Notification();
				notification.setForUser(userRepo.findById(propertyRepo.findById(id).getCreatedBy()).getLogin());
				notification.setNotificationText("Your property has a comment!");
				notification.setRedirUrl("/property?id="+id+"&interested=false");
				notification.setWhenCreated("today");
				notification.setNotificationType("Property Feedback");
				notification.setIsSeen(false);
				notifRepo.save(notification);
				return "redirect:/property?id="+id;
			}
		}
		
	}	
	
	/**
	   * method return string for junit controller test.
	   * @return string "Feedback"
	   */
	  public String getTestMessage(){
	    return "feedback";
	  }
}
