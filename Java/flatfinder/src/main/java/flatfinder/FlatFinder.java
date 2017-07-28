package flatfinder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FileCopyUtils;

import flatfinder.domain.Profile;
import flatfinder.domain.Property;
import flatfinder.domain.Role;
import flatfinder.domain.Room;
import flatfinder.domain.User;
import flatfinder.persistence.repository.ProfileRepository;
import flatfinder.persistence.repository.PropertyRepository;
import flatfinder.persistence.repository.RoleRepository;
import flatfinder.persistence.repository.RoomRepository;
import flatfinder.persistence.repository.UserRepository;

@SpringBootApplication
public class FlatFinder implements ApplicationRunner {
 
  public static String ROOT = "src/main/webapp/resources";
  
  public static void main(String[] args) {
    SpringApplication.run(FlatFinder.class, args);
  }
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private RoleRepository roleRepo;
  
  @Autowired
  private PropertyRepository propertyRepo;
  
  @Autowired
  private RoomRepository roomRepo;
  
  @Autowired
  private ProfileRepository profRepo;
    
  public static final int ROLE_ADMIN = 1;
  public static final int ROLE_LANDLORD = 2;
  public static final int ROLE_SEARCHER = 3;
  public static final int ROLE_SUSPENDED = 4;
  
  @Override
  public void run(ApplicationArguments args) throws Exception {
	  
	propertyRepo.deleteAll();
	  
    System.out.println("Application Init");
    String startingUserDB 		= "startingUserDB.txt";
    String startingPropertyDB 	= "startingPropertyDB.txt";
    String startingRoomDB		= "startingRoomDB.txt";
    try {
      List<User> userlist = new ArrayList<User>();
    	Role role = null;
    	User user = null;
    	Room room = null;
    	
    	Property property = null;
    	
    	Scanner newPropertyFile = new Scanner(new File(startingPropertyDB));
    	
      BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
      Scanner newUserFile = new Scanner(new File(startingUserDB));
     
           int count = 0;
           while(newUserFile.hasNextLine()){
             role = new Role();
             user = new User();
                newUserFile.nextLine();
             role.setId(newUserFile.nextInt());
               newUserFile.nextLine(); //Needed as nextInt() excludes carriage returns
             role.setRole(newUserFile.nextLine());
             user.setLogin(newUserFile.nextLine());
             user.setPassword(pe.encode(newUserFile.nextLine()));
             user.setRole(role);
             user.setAddress1(newUserFile.nextLine());
             user.setAddress2(newUserFile.nextLine());
             user.setAddress3(newUserFile.nextLine());
             user.setPostcode(newUserFile.nextLine());
             user.setBuddyUp(Integer.valueOf(newUserFile.nextLine()));
             user.setAgeGroup(Integer.valueOf(newUserFile.nextLine()));
             user.setStatus(Integer.valueOf(newUserFile.nextLine()));
             user.setEmail(newUserFile.nextLine());
             user.setGender(Integer.valueOf(newUserFile.nextLine()));
               newUserFile.nextLine();
             user.setTitle(newUserFile.nextLine());
             user.setFirstName(newUserFile.nextLine());
             user.setLastName(newUserFile.nextLine());
             user.setDateCreated();
             user.setLastActive(null);
             userlist.add(user);
             
           }
           newUserFile.close();
           for (User a : userlist) {
             userRepo.save(a);
             User usr = userRepo.findByLogin(a.getLogin());
             Profile profile = new Profile();
             profile.setUserid(usr.getId());
             profRepo.save(profile);
             
             new File(FlatFinder.ROOT + "/users/" + a.getLogin()).mkdirs();
              File sourceF = new File(FlatFinder.ROOT + "/img/female.png");
              File sourceM = new File(FlatFinder.ROOT + "/img/male.png");
              File dest = new File(FlatFinder.ROOT + "/users/" + a.getLogin() + "/" + a.getLogin() + "_profile.jpg");
              try {
                if (a.getGender() == 0) {
                  FileCopyUtils.copy(sourceF, dest);
                } else {
                  FileCopyUtils.copy(sourceM, dest);
                }
              } catch (IOException e) {
                  e.printStackTrace();
              }
           }
           
    	
    	while(newPropertyFile.hasNextLine()){
    		
    		
    		property = new Property();
    		
    		newPropertyFile.nextLine(); //Needed as the file has a gap
    		
    		property.setHouseNum(newPropertyFile.nextLine());
    		property.setAddress1(newPropertyFile.nextLine());
    		property.setAddress2(newPropertyFile.nextLine());
    		property.setAddress3(newPropertyFile.nextLine());
    		
    		property.setBillsIncluded(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setDescription(newPropertyFile.nextLine());
    		property.setLocalArea(newPropertyFile.nextLine());
    		property.setDisabledAccess(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setHeadline_text(newPropertyFile.nextLine());
    		
    		property.setNumViews(newPropertyFile.nextInt());
    			newPropertyFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		property.setNumberOfRooms(newPropertyFile.nextInt());
    			newPropertyFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		property.setNumberOfOccupiedRooms(newPropertyFile.nextInt());
    			newPropertyFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		property.setOccupantType(newPropertyFile.nextLine());
    		property.setParking(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setPetsAllowed(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setPostcode(newPropertyFile.nextLine());
    		property.setPricePerWeek(newPropertyFile.nextInt());
    			newPropertyFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		property.setQuietArea(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setShortTerm(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setSingleGender(Boolean.valueOf(newPropertyFile.nextLine()));
    		property.setSmoking(Boolean.valueOf(newPropertyFile.nextLine()));
    		
    		//Available from and to
    	
    		
    	    Date startDate = null;
    	    Date endDate = null;
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			startDate = sdf.parse(newPropertyFile.nextLine());
			endDate = sdf.parse(newPropertyFile.nextLine());
			
			property.setStartDate(startDate);
			property.setEndDate(endDate);
    		
    		
			property.setListed(Boolean.valueOf(newPropertyFile.nextLine()));
			
    		property.setType(newPropertyFile.nextInt());
    			newPropertyFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		property.setCreatedBy(newPropertyFile.nextInt());
    			newPropertyFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		
    		propertyRepo.save(property);
    	}
    	
    	
    	
    	newPropertyFile.close();
     
    	Scanner newRoomFile = new Scanner(new File(startingRoomDB));
    	
    	while(newRoomFile.hasNextLine()){
    		
    		room = new Room();
      
    		newRoomFile.nextLine(); //Needed as the file has a gap
    		
    		room.setPropertyId(newRoomFile.nextInt());
    			newRoomFile.nextLine(); //Needed as nextInt() excludes carriage returns
    		room.setFurnished(Boolean.valueOf(newRoomFile.nextLine()));
    		room.setDateCreated();
    		room.setHeadline(newRoomFile.nextLine());
    		room.setDescription(newRoomFile.nextLine());
    		room.setEnsuite(Boolean.valueOf(newRoomFile.nextLine()));
    		room.setOccupiedByUsername(newRoomFile.nextLine());
    		room.setWidth(newRoomFile.nextInt());
    		room.setHeight(newRoomFile.nextInt());
    		room.setDoubleRoom(Boolean.valueOf(newRoomFile.nextLine()));
    		
    		roomRepo.save(room);
    		
    	}
      newRoomFile.close();
    } catch (Exception e) {
      System.out.println(e);
    }
    
  }
  
  @Bean
  CommandLineRunner init() {
    return (String[] args) -> {
      new File(ROOT).mkdir();
    };
  }
}