package flatfinder.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import flatfinder.domain.Property;
import flatfinder.domain.Room;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.RoomRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class DashboardController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PropertyRepository propertyRepo;
	@Autowired
	private RoomRepository roomRepo;
	
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard/statistics", method = RequestMethod.GET)
		public ModelAndView Statistics(){
			ModelAndView modelAndView = new ModelAndView("propertyStatistics");
			Integer requesterId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
	
			List<Property> findStatisticsOn = propertyRepo.findByUserId(requesterId);
			
			modelAndView.addObject("statistics", findStatisticsOn);
			
			return modelAndView;
			
		}
	
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard", method = RequestMethod.GET)
		public ModelAndView landlordDashView(){
			ModelAndView modelAndView = new ModelAndView("/landlord-dashboard");
			return modelAndView;
		}
		
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard/create", method = RequestMethod.GET)
		public ModelAndView addPropertyLandLord(){	
			
			ModelAndView modelAndView;
			modelAndView = new ModelAndView("createSingleProperty");
			
			return modelAndView;
		}
		
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard/manage", method = RequestMethod.GET)
		public ModelAndView managePageLandLord(){
			ModelAndView modelAndView = new ModelAndView("manageProperties");
			Integer currentId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
			List<Property> currentLister = propertyRepo.findByUserId(currentId);
			modelAndView.addObject("listing", currentLister);
			
			return modelAndView;
		}
		
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard/manage/editProperty", method = RequestMethod.GET)
		public ModelAndView editPropertyLandLord(
				Model model,
				@RequestParam("id")Integer id
			){
			Integer requesterId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
			Property current = propertyRepo.findById(id);
			
			String startingDate = new SimpleDateFormat("dd-MM-yyyy").format(current.getStartDate());
			String endingDate = new SimpleDateFormat("dd-MM-yyyy").format(current.getEndDate());
			
			Gson gson = new Gson();
		    String startingDateString = gson.toJson(startingDate);
		    String endingDateString = gson.toJson(endingDate);
			
			ModelAndView modelAndView;
			if(requesterId == current.getCreatedBy()){
				modelAndView = new ModelAndView("editSingleProperty");
				modelAndView.addObject("currentProperty", current);
				String callUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+current.getPostcode().replaceAll("\\s","");
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
				//modelAndView.addObject("startDateString", startingDate);
				
				model.addAttribute("startDateString", startingDateString);
				model.addAttribute("endDateString", endingDateString);
				
			} else {
				modelAndView = new ModelAndView("error-message");
			}
			return modelAndView;
		}
	
	
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard/manage/deleteProperty", method = RequestMethod.GET)
		public String deletePropertyLandlord(
				@RequestParam("id")Integer id
			){
			
			Integer requesterId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
			Property current = propertyRepo.findById(id);
			
			List<Room> allRooms = roomRepo.findByPropertyId(id);
			
			if(requesterId == current.getCreatedBy()){
				//Deleting the room(s)
				for (int i = 0; i < allRooms.size(); i++) {
					roomRepo.delete(allRooms.get(i));
				}
				//Deleting the property
				propertyRepo.delete(current);
			} else {}
			
			return "redirect:";
		}
		
		
		//Landlord Dashboard
		@RequestMapping(value = "/landlord-dashboard/manage/unlistProperty", method = RequestMethod.GET)
		public String unlistPropertyLandlord(
				@RequestParam("id") 	Integer id,
				@RequestParam("listed") boolean listed
			){
			
			Integer requesterId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
			Property current = propertyRepo.findById(id);
			
			
			if(requesterId == current.getCreatedBy()){
				if(listed == false){
					current.setListed(false);
				}else{
					current.setListed(true);	
				}
				propertyRepo.save(current);		
			} else {
				//do nothing
			}
			
			return "redirect:";
		}
	
		//Admin Dashboard
		@RequestMapping(value="/admin-dashboard", method=RequestMethod.GET)
		public ModelAndView adminDashView(){
			ModelAndView modelAndView = new ModelAndView("/admin-dashboard");
			return modelAndView;
		}
		
		//Admin Dashboard
		@RequestMapping(value="/admin-dashboard/users/message-all", method=RequestMethod.GET)
		public ModelAndView adminMessageAll(){
			return new ModelAndView("adminMassMessage");
		}
		
		//Admin Dashboard
		@RequestMapping(value = "/admin-dashboard/listings/editProperty", method = RequestMethod.GET)
		public ModelAndView editProperty(Model model,
				@RequestParam("id")Integer id
			){
			Property current = propertyRepo.findById(id);
			ModelAndView modelAndView = null;
			if (null != current) {
	  		String startingDate = new SimpleDateFormat("dd-MM-yyyy").format(current.getStartDate());
	  		String endingDate = new SimpleDateFormat("dd-MM-yyyy").format(current.getEndDate());
	  		
	  		Gson gson = new Gson();
	  	    String startingDateString = gson.toJson(startingDate);
	  	    String endingDateString = gson.toJson(endingDate);
	  		
	  		modelAndView = new ModelAndView("editSingleProperty");
	  		modelAndView.addObject("currentProperty", current);
	  		
	  		model.addAttribute("startDateString", startingDateString);
	  		model.addAttribute("endDateString", endingDateString);
			}	
			return modelAndView;
		}
		
		
		//Admin Dashboard
		@RequestMapping(value = "/admin-dashboard/listings/editRoom", method = RequestMethod.GET)
		public ModelAndView editRoom(
				@RequestParam("id")Integer id,
				@RequestParam(value="roomid", required = false) Integer roomid			
			){
			Property current = propertyRepo.findById(id);
			ModelAndView modelAndView = null;
			if (null != current) {
			
			List<Room> currentLister = roomRepo.findByPropertyId(id);
			Room currentRoom = roomRepo.findById(roomid);
			
				modelAndView = new ModelAndView("editRoom");
				modelAndView.addObject("currentProperty", current);
				modelAndView.addObject("listing", currentLister);
				
				if(roomid != null){
					modelAndView.addObject("roomid", roomid);
					modelAndView.addObject("currentRoom", currentRoom);
				}
			}
			return modelAndView;
		}
		
		//Admin Dashboard
		@RequestMapping(value = "/admin-dashboard/listings/deleteRoom", method = RequestMethod.GET)
		public String deleteRoom(
				@RequestParam("id")Integer id,
				@RequestParam("roomid") Integer roomid
			){	
			
			Property current = propertyRepo.findById(id);
			Room currentRoom = roomRepo.findById(roomid);
			
			int numRooms = current.getNumberOfRooms();
			current.setNumberOfRooms(numRooms - 1);
			propertyRepo.save(current);
			
				if(roomid != null){
					roomRepo.delete(currentRoom);
				}
			
			return "redirect:";
		}
		
		//Admin Dashboard
		@RequestMapping(value = "/admin-dashboard/listings/deleteProperty", method = RequestMethod.GET)
		public String deleteProperty(
				@RequestParam("id")Integer id
			){
			
			Property current = propertyRepo.findById(id);
			List<Room> allRooms = roomRepo.findByPropertyId(id);
	
				//Deleting the room(s)
				for (int i = 0; i < allRooms.size(); i++) {
					roomRepo.delete(allRooms.get(i));
				}
				//Deleting the property
				propertyRepo.delete(current);
	
			
			return "redirect:";
		}
		
		//Admin Dashboard
		@RequestMapping(value = "/admin-dashboard/listings/unlistProperty", method = RequestMethod.GET)
		public String unlistPropertyAdmin(
				@RequestParam("id") 	Integer id,
				@RequestParam("listed") boolean listed
			){
			Property current = propertyRepo.findById(id);
			
	
				if(listed == false){
					current.setListed(false);
				}else{
					current.setListed(true);	
				}
				propertyRepo.save(current);		
			
			return "redirect:";
		}
		
		//Admin Dashboard
		@RequestMapping(value = "/admin-dashboard/listings", method = RequestMethod.GET)
		public ModelAndView getAdminListings(){
			ModelAndView modelAndView = new ModelAndView("adminListings");
			List<Property> allProps = (List<Property>) propertyRepo.findAll();
			modelAndView.addObject("allprops", allProps);
			return modelAndView;
		}
		
		@RequestMapping(value="/property/report", method = RequestMethod.GET)
		public ModelAndView propertyReportPage(){
			return new ModelAndView("propertyReportPage");
		}
		
    @RequestMapping(value="/user/report", method = RequestMethod.GET)
    public ModelAndView userReportPage(){
      return new ModelAndView("report-user");
    }

	
  /**
   * method return string for junit controller test.
   * @return string "dashboard"
   */
  public String getTestMessage(){
    return "dashboard";
  }
}
