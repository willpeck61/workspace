package flatfinder.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.domain.User;
import flatfinder.domain.Conversation;
import flatfinder.domain.Inbox;
import flatfinder.domain.Message;
import flatfinder.domain.Notification;
import flatfinder.persistence.repository.ConversationRepository;
import flatfinder.persistence.repository.InboxRepository;
import flatfinder.persistence.repository.MessageRepository;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class MessageController {

	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private NotificationRepository notifRepo;
	
	@Autowired
	private ConversationRepository convRepo;
	
	@Autowired
	private InboxRepository inboxRepo;
	
	@RequestMapping(value = "/message", method=RequestMethod.GET)
	public ModelAndView viewMessenger(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelAndView = new ModelAndView("/message");
	    String user = auth.getName();
		List<Conversation> getList = convRepo.findBySender(user);
		getList.addAll(convRepo.findByRecipient(user));
		List<Conversation> messageList = new ArrayList<>();
		modelAndView.addObject("messages", getList);
		return modelAndView;
	}
	
	@RequestMapping(value = "/message/create", method = RequestMethod.GET)
	public ModelAndView createMessage(){
		ModelAndView modelAndView = new ModelAndView("createMessage");
		modelAndView.addObject("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
		return modelAndView;
	}
	
	@RequestMapping(value = "/sendInitMessage", method = RequestMethod.POST)
	public String sendInitMessage(
			@RequestParam("userToSend")String username,
			@RequestParam("messageContent")String text
			){
		Message msg = new Message();
		Conversation conv = new Conversation();
		conv.setLastReceived(new Date().toString());
		conv.setRecipient(username);
		conv.setSender(SecurityContextHolder.getContext().getAuthentication().getName());
		conv.setNumberOfMessages(1);
		convRepo.save(conv);
		msg.setFrom(SecurityContextHolder.getContext().getAuthentication().getName());
		msg.setMessage(text);
		msg.setTime(new Date().toString());
		msg.setTo(username);
		msg.setConversationId(conv.getId());
		messageRepo.save(msg);
		Notification notif = new Notification();
		notif.setForUser(username);
		notif.setNotificationText("New Message");
		notif.setWhenCreated(new Date().toString());
		notif.setNotificationType("Message");
		notif.setRedirUrl("/message/view?id="+msg.getId());
		notifRepo.save(notif);
		return "redirect:message";
	}
	
	@RequestMapping(value = "/message/view", method = RequestMethod.GET)
	public ModelAndView viewMessages(
			@RequestParam("id")Integer id
			){

		List<Message> conversation = messageRepo.findByConversationId(id);
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		ModelAndView modelAndView;
		if(conversation.get(0).getFrom().toLowerCase().equals(user.toLowerCase()) || conversation.get(0).getTo().toLowerCase().equals(user.toLowerCase())){
			modelAndView = new ModelAndView("viewMessage");
			modelAndView.addObject("conversation", conversation);
		} else {
			modelAndView = new ModelAndView("error-message");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/message/nextMsg", method = RequestMethod.POST)
	public ModelAndView nextMessage(
			@RequestParam("conversation")Integer convId,
			@RequestParam("user")String username,
			@RequestParam("msg")String message
			){
		ModelAndView modelAndView = new ModelAndView("nextMessage");
		Message newMsg = new Message();
		newMsg.setFrom(SecurityContextHolder.getContext().getAuthentication().getName());
		newMsg.setTo(username);
		newMsg.setConversationId(convId);
		newMsg.setTime(new Date().toString());
		newMsg.setMessage(message);
		messageRepo.save(newMsg);
		modelAndView.addObject("id", convId);
		System.out.println("working");
		return modelAndView;
	}
	
	/*
	 * Mass messaging here. Can only be sent to SEARCHER and LANDLORD.
	 * */
	
	@RequestMapping(value="/admin-dashboard/users/message-all/message", method=RequestMethod.POST)
	public String adminMassMessageSubmit(
			@RequestParam("message") String message
			){
		
		List<User> allUsers = (List<User>) userRepo.findAll();
		List<User> toSendMessageTo = new ArrayList<>();

		for(User usr: allUsers){
			if(usr.getRole().getRole().equals("SEARCHER") || usr.getRole().getRole().equals("LANDLORD")){
				System.out.println("reaching this stage\n");
				toSendMessageTo.add(usr);
			}
		}
		for(User usr:toSendMessageTo){
			Inbox inbox = new Inbox();
			inbox.setSender(SecurityContextHolder.getContext().getAuthentication().getName());
			inbox.setReceiver(usr.getLogin());
			inbox.setCreation("now");
			inbox.setMessage(message);
			inboxRepo.save(inbox);
			
			Notification notification = new Notification();

			notification.setForUser(usr.getLogin());
			notification.setNotificationType("Admin Message");
			notification.setRedirUrl("/inbox/view?id="+inbox.getId());
			notification.setNotificationText("Message from admin");
			notifRepo.save(notification);
		}
		
		return "redirect:/admin-dashboard/users";
	}

	@RequestMapping(value="/inbox", method=RequestMethod.GET)
	public ModelAndView getInbox(){
		ModelAndView modelAndView = new ModelAndView("userInbox");
		modelAndView.addObject("msgs", inboxRepo.findByReceiver(SecurityContextHolder.getContext().getAuthentication().getName()));
		return modelAndView;
	}
	
	@RequestMapping(value="/inbox/view", method = RequestMethod.GET)
	public ModelAndView showInboxMessage(
			@RequestParam("id") Integer id
			){
		Inbox msg = inboxRepo.findById(id);
		if(msg.getReceiver().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
			ModelAndView modelAndView = new ModelAndView("viewInboxMessage");
			modelAndView.addObject("msg", msg);
			return modelAndView;
		} else {
			return new ModelAndView("error-message");
		}
	}
	
  /**
   * method return string for junit controller test.
   * @return string "session"
   */
  public String getTestMessage(){
    return "message";
  }
}
