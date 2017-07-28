package flatfinder.controller;


import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.FlatFinder;
import flatfinder.domain.Notification;
import flatfinder.domain.Property;
import flatfinder.domain.PropertyFeedback;
import flatfinder.domain.Room;
import flatfinder.domain.User;
import flatfinder.domain.UserPropertyHistory;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.PropertyFeedbackRepository;
import flatfinder.persistence.repository.PropertyInterestRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.RoomRepository;
import flatfinder.persistence.repository.UserPropertyHistoryRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class PropertyController {
	
	@Autowired
	private PropertyRepository propertyRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private NotificationRepository notifRepo;
	@Autowired
	private RoomRepository roomRepo;
	@Autowired
	private PropertyFeedbackRepository feedbackRepo;
	@Autowired
	private PropertyInterestRepository propertyInterestRepo;
	@Autowired
	private UserPropertyHistoryRepository userPropertyHistoryRepo;
	
	final String OLD_FORMAT = "dd/MM/yyyy";
	
	@RequestMapping(value="/property", method = RequestMethod.GET)
	public ModelAndView specificProperty(
			@RequestParam(value = "id", required = true) Integer id){
		
		ModelAndView modelAndView = null;
		if (null == propertyRepo.findById(id)) { return modelAndView; } else {
	    modelAndView = new ModelAndView();
	    String postcode = propertyRepo.findById(id).getPostcode();
	    Property thisProperty = propertyRepo.findById(id);
	    Integer currentId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
	    
	    thisProperty.setNumViews(thisProperty.getNumViews()+1);
	    propertyRepo.save(thisProperty);
	    
	    String callUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+postcode.replaceAll("\\s","");
	    //HashMap<String, String> output = new HashMap<>();
	    try {
	      String out = new Scanner(new URL(callUrl).openStream()).useDelimiter("\\A").next();
	      JSONObject json = new JSONObject(out);
	      JSONArray results = json.getJSONArray("results");
	      JSONObject location = results.getJSONObject(0);
	      modelAndView.addObject("lng", location.getJSONObject("geometry").getJSONObject("location").getString("lng"));
	      modelAndView.addObject("lat", location.getJSONObject("geometry").getJSONObject("location").getString("lat"));
	      //System.out.println(location.getJSONObject("geometry").getJSONObject("location").getString("lng"));
	    } catch (Exception e){
	      e.printStackTrace();
	    }
	    
	    List<PropertyFeedback> feedback = feedbackRepo.findByPropertyId(id);

	    boolean interested;
	    
	    if(!propertyInterestRepo.findByUserIdAndPropertyId(currentId, id).isEmpty()){
	      interested = true;
	    }else{
	      interested = false;
	    }
	    
	    
	    String hasFloorPlan;
	    
	     Image image = new ImageIcon(FlatFinder.ROOT + "/properties/"+thisProperty.getId()+"/"+thisProperty.getId()+"_floorPlan.jpg").getImage();
	        if(image.getWidth(null) == -1){
	               hasFloorPlan = "no";
	          }
	        else{
	          hasFloorPlan = "yes";
	          }
	        
	    //Adding the property to the users history
	    int smallestId = 0;
	        
	    UserPropertyHistory viewedProperty = userPropertyHistoryRepo.findByUserIdAndPropertyId(currentId, id);
	    if(viewedProperty != null){
	    	viewedProperty.setNumViews(viewedProperty.getNumViews() + 1);
	    	viewedProperty.setDateViewed();
	    }else{
	    	List<UserPropertyHistory> listUserPropertyHistory = userPropertyHistoryRepo.findByUserId(currentId);
	    	if(listUserPropertyHistory.size() < 10){
			    UserPropertyHistory uph = new UserPropertyHistory();		    
			    uph.setUserId(currentId);
			    uph.setNumViews(1);
			    uph.setPropertyId(id);
			    userPropertyHistoryRepo.save(uph);
	    	}else{
	    		for (int i = 0; i < listUserPropertyHistory.size(); i++) {
	    			if(listUserPropertyHistory.get(i).getId() < smallestId){
	    				smallestId = listUserPropertyHistory.get(i).getId();
	    			}
	    		}
	    		userPropertyHistoryRepo.delete(listUserPropertyHistory.get(smallestId));
	    		UserPropertyHistory uph = new UserPropertyHistory();		    
			    uph.setUserId(currentId);
			    uph.setNumViews(1);
			    uph.setPropertyId(id);
			    userPropertyHistoryRepo.save(uph);
	    	}
	    }
	        
	    List<Room> propertyRooms = roomRepo.findByPropertyId(id);
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userRepo.findByLogin(auth.getName());
	    String username = user.getLogin();
	    
	    modelAndView.addObject("interested", interested);
	    modelAndView.addObject("information", thisProperty);
	    modelAndView.addObject("propertyRooms", propertyRooms);
	    modelAndView.addObject("postcode", postcode);
	    modelAndView.addObject("feedback", feedback);
	    modelAndView.addObject("hasFloorPlan", hasFloorPlan);
	    modelAndView.addObject("username", username);
	    return modelAndView;
		}
	}
	
	@RequestMapping(value = "/submitEditedProperty", method = RequestMethod.POST)
	public String catchAdditions(
			@RequestParam("id") Integer Id,
			
			@RequestParam(value="price",required = false) Double price,
			@RequestParam(value="numberOfRooms", required = false) Integer rooms,
			@RequestParam(value="houseNum", required = false) String houseNum,
			@RequestParam(value="address1", required = false) String address1,
			@RequestParam(value="address2", required = false) String address2,
			@RequestParam(value="address3", required = false) String address3,
			@RequestParam(value="postcode", required = false) String postcode,
			@RequestParam(value="fromDD", required = false) Integer fromDD,
			@RequestParam(value="fromMM", required = false) Integer fromMM,
			@RequestParam(value="fromYYYY", required = false) Integer fromYYYY,
			@RequestParam(value="toDD", required = false) Integer toDD,
			@RequestParam(value="toMM", required = false) Integer toMM,
			@RequestParam(value="toYYYY", required = false) Integer toYYYY,
			@RequestParam(value="propertytype", required = false) Integer propertytype,
			@RequestParam(value="advertTitle", required = false) String headline,
			@RequestParam(value="description",required = false) String description,
			@RequestParam(value="localArea",required = false) String localAreaDesc,
			
			@RequestParam(value="file",required = false) MultipartFile file,
			@RequestParam(value="floorPlan",required = false) MultipartFile floorPlan,
			@RequestParam(value="type",required = false) String type,
			
			@RequestParam(value="occupantType",required = false) String occupantType,
			
			@RequestParam(value="billsincluded",required = false) boolean billsIncluded,
			@RequestParam(value="petsallowed",required = false) boolean petsAllowed,
			@RequestParam(value="parking",required = false) boolean parking,
			@RequestParam(value="disabledaccess",required = false) boolean disabledAccess,
			@RequestParam(value="smoking",required = false) boolean smoking,
			@RequestParam(value="quietarea",required = false) boolean quietArea,
			@RequestParam(value="shortterm",required = false) boolean shortTerm,
			@RequestParam(value="singlegender",required = false) boolean singleGender,
			
			@RequestParam(value="location",required = false) String location
			){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    Date startDate = null;
	    Date endDate = null;
		try {
			startDate = sdf.parse(fromDD +"-" + fromMM +  "-" + fromYYYY);
			endDate = sdf.parse(toDD +"-" + toMM +  "-" + toYYYY);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Property property = propertyRepo.findById(Id);
		
		property.setPricePerWeek(price.intValue());
		property.setNumberOfRooms(rooms);
		property.setHouseNum(houseNum);
		property.setAddress1(address1);
		property.setAddress2(address2);
		property.setAddress3(address3);
		property.setPostcode(postcode.replaceAll("\\s",""));
		property.setNumberOfRooms(0);
		property.setStartDate(startDate);
		property.setEndDate(endDate);
		property.setType(propertytype);
		
		property.setOccupantType(occupantType);
		
		
		property.setBillsIncluded(billsIncluded);
		property.setPetsAllowed(petsAllowed);
		property.setParking(parking);
		property.setDisabledAccess(disabledAccess);
		property.setSmoking(smoking);
		property.setQuietArea(quietArea);
		property.setShortTerm(shortTerm);
		property.setSingleGender(singleGender);
				
		property.setHeadline_text(headline);
		property.setDescription(description);
		property.setLocalArea(localAreaDesc);
		propertyRepo.save(property);
				
		
		String name = "properties/"+property.getId()+"/"+property.getId()+"_main.jpg";
		String floorPlanPath = "properties/"+property.getId()+"/"+property.getId()+"_floorPlan.jpg";
		
		if (!file.isEmpty()) {
	      try {
	        BufferedOutputStream stream = new BufferedOutputStream(
	            new FileOutputStream(new File(FlatFinder.ROOT + "/" + name)));
	                FileCopyUtils.copy(file.getInputStream(), stream);
	        stream.close();
	      }
	      catch (Exception e) {System.out.println(e);}
	    }else {}

			//Uploading floorPlan picture	
		if (!floorPlan.isEmpty()) {
	      try {
	        BufferedOutputStream stream = new BufferedOutputStream(
	            new FileOutputStream(new File(FlatFinder.ROOT + "/" + floorPlanPath)));
	                FileCopyUtils.copy(floorPlan.getInputStream(), stream);
	        stream.close();
	      }catch (Exception e) {System.out.println(e);}
	    }else {System.out.println("fail");}
		
		
		return "redirect:"+location;
	}
	
	@RequestMapping(value = "/submitAddedProperty", method = RequestMethod.POST)
	public String catchNewPropertyAdditions(
			@RequestParam(value="price",required = false) Double price,
			@RequestParam(value="numberOfRooms", required = false) Integer rooms,
			@RequestParam(value="houseNum", required = false) String houseNum,			
			@RequestParam(value="address1", required = false) String address1,
			@RequestParam(value="address2", required = false) String address2,
			@RequestParam(value="address3", required = false) String address3,
			@RequestParam(value="postcode", required = false) String postcode,
			@RequestParam(value="fromDD", required = false) Integer fromDD,
			@RequestParam(value="fromMM", required = false) Integer fromMM,
			@RequestParam(value="fromYYYY", required = false) Integer fromYYYY,
			@RequestParam(value="toDD", required = false) Integer toDD,
			@RequestParam(value="toMM", required = false) Integer toMM,
			@RequestParam(value="toYYYY", required = false) Integer toYYYY,
			@RequestParam(value="propertytype", required = false) Integer propertytype,
			@RequestParam(value="advertTitle", required = false) String headline,
			@RequestParam(value="description",required = false) String description,
			@RequestParam(value="localArea",required = false) String localAreaDesc,
			
			@RequestParam(value="file",required = false) MultipartFile file,
			@RequestParam(value="floorPlan",required = false) MultipartFile floorPlan,
			@RequestParam(value="type",required = false) String type,
			
			@RequestParam(value="occupantType",required = false) String occupantType,
			
			@RequestParam(value="billsincluded",required = false) boolean billsIncluded,
			@RequestParam(value="petsallowed",required = false) boolean petsAllowed,
			@RequestParam(value="parking",required = false) boolean parking,
			@RequestParam(value="disabledaccess",required = false) boolean disabledAccess,
			@RequestParam(value="smoking",required = false) boolean smoking,
			@RequestParam(value="quietarea",required = false) boolean quietArea,
			@RequestParam(value="shortterm",required = false) boolean shortTerm,
			@RequestParam(value="singlegender",required = false) boolean singleGender
			
			){
					
		Integer currentId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
		String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
				
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    Date startDate = null;
	    Date endDate = null;
	    Notification notify = new Notification();
	    
	    String startDateString = fromDD +"/" + fromMM +  "/" + fromYYYY;
	    String endDateString = toDD +"/" + toMM +  "/" + toYYYY;
	    
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			startDate = sdf.parse(startDateString);
			endDate = sdf.parse(endDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    //String date=sdf.format(startDate); //converts to dd-mm-yyyy
		
		notify.setForUser(currentName);
		notify.setNotificationText("New property added in "+address1);
		notify.setNotificationType("New Property");
		notify.setWhenCreated("today");
		
		Property property = new Property();
		property.setPricePerWeek(price.intValue());
		property.setNumberOfRooms(rooms);
		property.setNumberOfOccupiedRooms(0);
		property.setListed(true);
		property.setHouseNum(houseNum);
		property.setAddress1(address1);
		property.setAddress2(address2);
		property.setAddress3(address3);
		property.setPostcode(postcode.replaceAll("\\s",""));
		property.setNumberOfRooms(0);
		property.setStartDate(startDate);
		property.setEndDate(endDate);
		property.setType(propertytype);
		
		property.setOccupantType(occupantType);
		
		
		property.setBillsIncluded(billsIncluded);
		property.setPetsAllowed(petsAllowed);
		property.setParking(parking);
		property.setDisabledAccess(disabledAccess);
		property.setSmoking(smoking);
		property.setQuietArea(quietArea);
		property.setShortTerm(shortTerm);
		property.setSingleGender(singleGender);
				
		property.setHeadline_text(headline);
		property.setDescription(description);
		property.setLocalArea(localAreaDesc);
		property.setCreatedBy(currentId);
		
		propertyRepo.save(property);
		notify.setRedirUrl("/property?id="+property.getId()+"&interested=false");
		notifRepo.save(notify);

		new File(FlatFinder.ROOT + "/properties/" + property.getId()).mkdirs();
		
		String name = "properties/"+property.getId()+"/"+property.getId()+"_main.jpg";
		String floorPlanPath = "properties/"+property.getId()+"/"+property.getId()+"_floorPlan.jpg";
		
		if (!file.isEmpty()) {
		      try {
		        BufferedOutputStream stream = new BufferedOutputStream(
		            new FileOutputStream(new File(FlatFinder.ROOT + "/" + name)));
		                FileCopyUtils.copy(file.getInputStream(), stream);
		        stream.close();
		      }
		      catch (Exception e) { System.out.println(e); }
		    }else { System.out.println("fail"); }
		

			//Uploading floorPlan picture	
		if (!floorPlan.isEmpty()) {
	      try {
	        BufferedOutputStream stream = new BufferedOutputStream(
	            new FileOutputStream(new File(FlatFinder.ROOT + "/" + floorPlanPath)));
	                FileCopyUtils.copy(floorPlan.getInputStream(), stream);
	        stream.close();
	      }catch (Exception e) {System.out.println(e);}
	    }else {System.out.println("fail");}
			
		
		return "redirect:landlord-dashboard/manage";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/submitAddedPropertyPWOI", method = RequestMethod.POST)
	public String catchNewPropertyAdditionsPWOI(
			@RequestParam(value="price",required = false) Double price,
			@RequestParam(value="numberOfRooms", required = false) Integer rooms,
			@RequestParam(value="houseNum", required = false) String houseNum,			
			@RequestParam(value="address1", required = false) String address1,
			@RequestParam(value="address2", required = false) String address2,
			@RequestParam(value="address3", required = false) String address3,
			@RequestParam(value="postcode", required = false) String postcode,
			@RequestParam(value="fromDD", required = false) Integer fromDD,
			@RequestParam(value="fromMM", required = false) Integer fromMM,
			@RequestParam(value="fromYYYY", required = false) Integer fromYYYY,
			@RequestParam(value="toDD", required = false) Integer toDD,
			@RequestParam(value="toMM", required = false) Integer toMM,
			@RequestParam(value="toYYYY", required = false) Integer toYYYY,
			@RequestParam(value="propertytype", required = false) Integer propertytype,
			@RequestParam(value="advertTitle", required = false) String headline,
			@RequestParam(value="description",required = false) String description,
			@RequestParam(value="localArea",required = false) String localAreaDesc,
			
			@RequestParam(value="occupantType",required = false) String occupantType,
			
			@RequestParam(value="billsincluded",required = false) boolean billsIncluded,
			@RequestParam(value="petsallowed",required = false) boolean petsAllowed,
			@RequestParam(value="parking",required = false) boolean parking,
			@RequestParam(value="disabledaccess",required = false) boolean disabledAccess,
			@RequestParam(value="smoking",required = false) boolean smoking,
			@RequestParam(value="quietarea",required = false) boolean quietArea,
			@RequestParam(value="shortterm",required = false) boolean shortTerm,
			@RequestParam(value="singlegender",required = false) boolean singleGender,
			
			@RequestParam(value="currentName",required = false) String currentName,
			@RequestParam(value="currentId",required = false) Integer currentId
			
			){
					
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    Date startDate = null;
	    Date endDate = null;
	    Notification notify = new Notification();
	    
	    String startDateString = fromDD +"/" + fromMM +  "/" + fromYYYY;
	    String endDateString = toDD +"/" + toMM +  "/" + toYYYY;
	    
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			startDate = sdf.parse(startDateString);
			endDate = sdf.parse(endDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    //String date=sdf.format(startDate); //converts to dd-mm-yyyy
		
		notify.setForUser(currentName);
		notify.setNotificationText("New property added in "+address1);
		notify.setNotificationType("New Property");
		notify.setWhenCreated("today");
		
		Property property = new Property();
		property.setPricePerWeek(price.intValue());
		property.setNumberOfRooms(0);
		property.setHouseNum(houseNum);
		property.setAddress1(address1);
		property.setAddress2(address2);
		property.setAddress3(address3);
		property.setPostcode(postcode.replaceAll("\\s",""));
		property.setNumberOfRooms(0);
		property.setStartDate(startDate);
		property.setEndDate(endDate);
		property.setType(propertytype);
		
		property.setOccupantType(occupantType);
		
		
		property.setBillsIncluded(billsIncluded);
		property.setPetsAllowed(petsAllowed);
		property.setParking(parking);
		property.setDisabledAccess(disabledAccess);
		property.setSmoking(smoking);
		property.setQuietArea(quietArea);
		property.setShortTerm(shortTerm);
		property.setSingleGender(singleGender);
				
		property.setHeadline_text(headline);
		property.setDescription(description);
		property.setLocalArea(localAreaDesc);
		property.setCreatedBy(currentId);
		
		propertyRepo.save(property);
		notify.setRedirUrl("/property?id="+property.getId()+"&interested=false");
		notifRepo.save(notify);

		
		return "redirect:landlord-dashboard/manage";
	}
	
	
	@RequestMapping(value = "/submitEditedPropertyPWOI", method = RequestMethod.POST)
	public String catchAdditionsPWOI(
			@RequestParam("id") Integer Id,
			
			@RequestParam(value="price",required = false) Double price,
			@RequestParam(value="numberOfRooms", required = false) Integer rooms,
			@RequestParam(value="houseNum", required = false) String houseNum,			
			@RequestParam(value="address1", required = false) String address1,
			@RequestParam(value="address2", required = false) String address2,
			@RequestParam(value="address3", required = false) String address3,
			@RequestParam(value="postcode", required = false) String postcode,
			@RequestParam(value="fromDD", required = false) Integer fromDD,
			@RequestParam(value="fromMM", required = false) Integer fromMM,
			@RequestParam(value="fromYYYY", required = false) Integer fromYYYY,
			@RequestParam(value="toDD", required = false) Integer toDD,
			@RequestParam(value="toMM", required = false) Integer toMM,
			@RequestParam(value="toYYYY", required = false) Integer toYYYY,
			@RequestParam(value="propertytype", required = false) Integer propertytype,
			@RequestParam(value="advertTitle", required = false) String headline,
			@RequestParam(value="description",required = false) String description,
			@RequestParam(value="localArea",required = false) String localAreaDesc,
			
			@RequestParam(value="occupantType",required = false) String occupantType,
			
			@RequestParam(value="billsincluded",required = false) boolean billsIncluded,
			@RequestParam(value="petsallowed",required = false) boolean petsAllowed,
			@RequestParam(value="parking",required = false) boolean parking,
			@RequestParam(value="disabledaccess",required = false) boolean disabledAccess,
			@RequestParam(value="smoking",required = false) boolean smoking,
			@RequestParam(value="quietarea",required = false) boolean quietArea,
			@RequestParam(value="shortterm",required = false) boolean shortTerm,
			@RequestParam(value="singlegender",required = false) boolean singleGender,
			
			@RequestParam(value="location",required = false) String location
			){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    Date startDate = null;
	    Date endDate = null;
		try {
			startDate = sdf.parse(fromDD +"-" + fromMM +  "-" + fromYYYY);
			endDate = sdf.parse(toDD +"-" + toMM +  "-" + toYYYY);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Property property = propertyRepo.findById(Id);
		
		property.setPricePerWeek(price.intValue());
		property.setNumberOfRooms(rooms);
		property.setHouseNum(houseNum);
		property.setAddress1(address1);
		property.setAddress2(address2);
		property.setAddress3(address3);
		property.setPostcode(postcode.replaceAll("\\s",""));
		property.setNumberOfRooms(0);
		property.setStartDate(startDate);
		property.setEndDate(endDate);
		property.setType(propertytype);
		
		property.setOccupantType(occupantType);
		
		
		property.setBillsIncluded(billsIncluded);
		property.setPetsAllowed(petsAllowed);
		property.setParking(parking);
		property.setDisabledAccess(disabledAccess);
		property.setSmoking(smoking);
		property.setQuietArea(quietArea);
		property.setShortTerm(shortTerm);
		property.setSingleGender(singleGender);
				
		property.setHeadline_text(headline);
		property.setDescription(description);
		property.setLocalArea(localAreaDesc);
		propertyRepo.save(property);

		return "redirect:"+location;
	}
	
	
  /**
   * method return string for junit controller test.
   * @return string "session"
   */	
  public String getTestMessage(){
    return "property";
  }
}
