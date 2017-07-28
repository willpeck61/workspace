package flatfinder.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.FlatFinder;
import flatfinder.domain.Property;
import flatfinder.domain.Room;
import flatfinder.persistence.repository.NotificationRepository;
import flatfinder.persistence.repository.PropertyFeedbackRepository;
import flatfinder.persistence.repository.PropertyInterestRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.RoomRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class RoomController {
	
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
			
			final String OLD_FORMAT = "dd/MM/yyyy";
	
	
			@RequestMapping(value = "/submitAddedRoom", method = RequestMethod.POST)
			public String catchNewRoomAdditions(
			@RequestParam(value="propertyid") 	int propertyID,
			@RequestParam(value="furnished")	boolean furnished,
			@RequestParam(value="headline") 	String headline,
			@RequestParam(value="description") 	String description,
			
			@RequestParam(value="file",required = false) MultipartFile file,
			@RequestParam(value="floorPlan",required = false) MultipartFile floorPlan,
			@RequestParam(value="type",required = false) String type,
			
			@RequestParam(value="ensuite") 		boolean ensuite,
			@RequestParam(value="width") 		int width,
			@RequestParam(value="height") 		int height,
			@RequestParam(value="doubleRoom") 	boolean doubleRoom,
			
			@RequestParam(value="location") 	String location
			){
		
			Property property = propertyRepo.findById(propertyID);
			int numRooms = property.getNumberOfRooms();
			property.setNumberOfRooms(numRooms + 1);
			propertyRepo.save(property);
		
			Room room = new Room();
			room.setPropertyId(propertyID);
			room.setFurnished(furnished);
			room.setHeadline(headline);
			room.setDescription(description);
			room.setEnsuite(ensuite);
			room.setWidth(width);
			room.setHeight(height);
			room.setDoubleRoom(doubleRoom);
			room.setOccupiedByUsername("null");
			
			roomRepo.save(room);
			
			new File(FlatFinder.ROOT + "/properties/" + propertyID + "/rooms/").mkdirs();
			
			String name = "properties/" + propertyID + "/rooms/room_" + room.getId() + ".jpg";
			String floorPlanPath = "properties/" + propertyID + "/rooms/room_" + room.getId() + "_floorPlan.jpg";
			
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
	
	@RequestMapping(value = "/submitUpdateRoom", method = RequestMethod.POST)
	public String catcUpdateRoomAdditions(
			@RequestParam(value="propertyid") 	int propertyID,
			@RequestParam(value="roomid") 		int roomID,
			@RequestParam(value="furnished")	boolean furnished,
			@RequestParam(value="headline") 	String headline,
			@RequestParam(value="description") 	String description,
			
			@RequestParam(value="file",required = false) MultipartFile file,
			@RequestParam(value="floorPlan",required = false) MultipartFile floorPlan,
			@RequestParam(value="type",required = false) String type,
			
			@RequestParam(value="ensuite") 		boolean ensuite,
			@RequestParam(value="width") 		int width,
			@RequestParam(value="height") 		int height,
			@RequestParam(value="doubleRoom") 	boolean doubleRoom,
			
			@RequestParam(value="location") 	String location
			){
		
		
			Room room = roomRepo.findById(roomID);
			room.setPropertyId(propertyID);
			room.setFurnished(furnished);
			room.setHeadline(headline);
			room.setDescription(description);
			room.setEnsuite(ensuite);
			room.setWidth(width);
			room.setHeight(height);
			room.setDoubleRoom(doubleRoom);
			
			roomRepo.save(room);
			
			
			String name = "properties/" + propertyID + "/rooms/room_" + room.getId() + ".jpg";
			String floorPlanPath = "properties/" + propertyID + "/rooms/room_" + room.getId() + "_floorPlan.jpg";
			
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
			//return editRoom(propertyID, null);
	}
	
	@RequestMapping(value = "/landlord-dashboard/manage/editRoom", method = RequestMethod.GET)
	public ModelAndView editRoom(
			@RequestParam("id")Integer id,
			@RequestParam(value="roomid", required = false) Integer roomid			
		){
		Integer requesterId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
		Property current = propertyRepo.findById(id);
		
		
		List<Room> currentLister = roomRepo.findByPropertyId(id);
		Room currentRoom = roomRepo.findById(roomid);
		
		
		ModelAndView modelAndView;
		if(requesterId == current.getCreatedBy()){
			modelAndView = new ModelAndView("editRoom");
			modelAndView.addObject("currentProperty", current);
			modelAndView.addObject("listing", currentLister);
			
			if(roomid != null){
				modelAndView.addObject("roomid", roomid);
				modelAndView.addObject("currentRoom", currentRoom);
			}
			
		} else {
			modelAndView = new ModelAndView("error-message");
		}
		return modelAndView;
	}
	
	
	
	
	
		@RequestMapping(value = "/submitAddedRoomRWOI", method = RequestMethod.POST)
		public String catchNewRoomAdditionsRWOI(
		@RequestParam(value="propertyid") 	Integer propertyID,
		@RequestParam(value="furnished")	boolean furnished,
		@RequestParam(value="headline") 	String headline,
		@RequestParam(value="description") 	String description,
		
		@RequestParam(value="ensuite") 		boolean ensuite,
		@RequestParam(value="width") 		Integer width,
		@RequestParam(value="height") 		Integer height,
		@RequestParam(value="doubleRoom") 	boolean doubleRoom,
		
		@RequestParam(value="location") 	String location
		){
	
		Property property = propertyRepo.findById(propertyID);
		int numRooms = property.getNumberOfRooms();
		property.setNumberOfRooms(numRooms + 1);
		propertyRepo.save(property);
	
		Room room = new Room();
		room.setPropertyId(propertyID);
		room.setFurnished(furnished);
		room.setHeadline(headline);
		room.setDescription(description);
		room.setEnsuite(ensuite);
		room.setWidth(width);
		room.setHeight(height);
		room.setDoubleRoom(doubleRoom);
		room.setOccupiedByUsername("null");
		
		roomRepo.save(room);
	
		return "redirect:"+location;
	}
		
		@RequestMapping(value = "/submitUpdateRoomRWOI", method = RequestMethod.POST)
		public String catcUpdateRoomAdditionsRWOI(
				@RequestParam(value="propertyid") 	Integer propertyID,
				@RequestParam(value="roomid") 		Integer roomID,
				@RequestParam(value="furnished")	boolean furnished,
				@RequestParam(value="headline") 	String headline,
				@RequestParam(value="description") 	String description,				
				
				@RequestParam(value="ensuite") 		boolean ensuite,
				@RequestParam(value="width") 		Integer width,
				@RequestParam(value="height") 		Integer height,
				@RequestParam(value="doubleRoom") 	boolean doubleRoom,
				
				@RequestParam(value="location") 	String location
				){
			
			
				Room room = roomRepo.findById(roomID);
				room.setPropertyId(propertyID);
				room.setFurnished(furnished);
				room.setHeadline(headline);
				room.setDescription(description);
				room.setEnsuite(ensuite);
				room.setWidth(width);
				room.setHeight(height);
				room.setDoubleRoom(doubleRoom);
				
				roomRepo.save(room);				
				
				return "redirect:"+location;
		}
		
	
	
	@RequestMapping(value = "/landlord-dashboard/deleteRoom", method = RequestMethod.GET)
	public ModelAndView deleteRoom(
			@RequestParam("id")Integer id,
			@RequestParam("roomid") Integer roomid
		){	
		
		Integer requesterId = userRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
		Property current = propertyRepo.findById(id);
		
		Room currentRoom = roomRepo.findById(roomid);
		
		int numRooms = current.getNumberOfRooms();
		current.setNumberOfRooms(numRooms - 1);
		propertyRepo.save(current);
		
		if(requesterId == current.getCreatedBy()){
			if(roomid != null){
				roomRepo.delete(currentRoom);
			}
		}
		
		return editRoom(id, null);
	}
	
 /**
   * method return string for junit controller test.
   * @return string "room"
   */
  public String getTestMessage(){
    return "room";
  }
	
}
