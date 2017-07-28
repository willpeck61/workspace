package flatfinder.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import flatfinder.domain.Property;
import flatfinder.persistence.repository.PropertyRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
  @Autowired
  private PropertyRepository propRepo;

  Property property;

  /**
   * Handles the request from the user when searching for a property. The user
   * is redirected to property_results.
   * 
   * @return view property_results.jsp
   * @throws ParseException
   **/
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public ModelAndView searchProperties(final Model model, 
	  @RequestParam(value = "CAP", required = false) final String CAP,
		  
      @RequestParam(value = "address", required = false) final String address,
      @RequestParam(value = "city", required = false) final String city,
      @RequestParam(value = "postcode", required = false) final String postcode,

      @RequestParam(value = "propertyType", required = false) final int propertyType,
      @RequestParam(value = "occupantType", required = false) final String occupantType,

      @RequestParam(value = "startDate", required = false) final String startDate,
      @RequestParam(value = "endDate", required = false) final String endDate,

      @RequestParam(value = "bills_included", required = false) final boolean billsIncluded,
      @RequestParam(value = "pets_allowed", required = false) final boolean petsAllowed,
      @RequestParam(value = "parking", required = false) final boolean parking,
      @RequestParam(value = "disabled_access", required = false) final boolean disabledAccess,
      @RequestParam(value = "smoking", required = false) final boolean smoking,
      @RequestParam(value = "quiet_area", required = false) final boolean quietArea,
      @RequestParam(value = "short_term", required = false) final boolean shortTerm,
      @RequestParam(value = "singleGender", required = false) final boolean singleGender,

      @RequestParam(value = "minPrice", required = false) final int minPrice,
      @RequestParam(value = "maxPrice", required = false) final int maxPrice,
      @RequestParam(value = "minNumRooms", required = false) final int minNumRooms,
      @RequestParam(value = "maxNumRooms", required = false) final int maxNumRooms,

      @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageno) throws ParseException {

    List<Property> property1 = null;
    List<Property> propertyResults = null;
    int pagecount = 0;
    Integer indexStart = null;
    Integer indexFinish = null;
    
    if(CAP.replaceAll("\\s", "").length() == 2 || CAP.replaceAll("\\s", "").length() == 3 || CAP.replaceAll("\\s", "").length() == 4){
    	
    	final Iterable<Property> shortPostcodeProperty = propRepo.findAll();

        final List<Property> allShortPostcodes = new ArrayList<>();
        for (final Property prop : shortPostcodeProperty) {
        	if(prop.getPostcode().substring(0,CAP.replaceAll("\\s", "").length()).toLowerCase().equals(CAP.replaceAll("\\s", "").toLowerCase())){
        		allShortPostcodes.add(prop);
        	}
          }
    	
        property1 = allShortPostcodes;       
    	
    }else if(!CAP.isEmpty() && city.isEmpty() && address.isEmpty() && postcode.isEmpty()){
    	//Seeing if CAP is a city
    	property1 = this.propRepo.findByAddress3(CAP.toLowerCase());
    	if(property1.isEmpty()){
    		//If empty, then test to see if address
    		property1 = this.propRepo.findByAddress1(CAP.toLowerCase());
    		if(property1.isEmpty()){
    			//If empty, then test to see if postcode
    			
    			final Iterable<Property> shortPostcodeProperty = this.propRepo.findAll();

    	        final List<Property> allShortPostcodes = new ArrayList<>();
    	        for (final Property prop : shortPostcodeProperty) {
    	        	if(prop.getPostcode().substring(0,CAP.replaceAll("\\s", "").length()).toLowerCase().equals(CAP.replaceAll("\\s", "").toLowerCase())){
    	             	allShortPostcodes.add(prop);
    	        	}
    	          }
    	    	
    	        property1 = allShortPostcodes;    
    		}
    	}
    }else{
	    if (!postcode.isEmpty()) {
	    	
	    	final Iterable<Property> shortPostcodeProperty = this.propRepo.findAll();

	        final List<Property> allShortPostcodes = new ArrayList<>();
	        for (final Property prop : shortPostcodeProperty) {
	        	if(prop.getPostcode().substring(0,CAP.replaceAll("\\s", "").length()).toLowerCase().equals(CAP.replaceAll("\\s", "").toLowerCase())){
	             	allShortPostcodes.add(prop);
	        	}
	          }
	    	
	        property1 = allShortPostcodes;  
	
	      if (!city.isEmpty()) {
	        if (!address.isEmpty()) {
	          for (int i = 0; i < property1.size(); i++) {
	            if ((!((Property) property1.get(i)).getAddress1().toLowerCase().equals(address.toLowerCase()))
	                && (!((Property) property1.get(i)).getAddress3().toLowerCase()
	                    .equals(city.toLowerCase()))) {
	              property1.remove(property1.get(i));
	            }
	          }
	        } else {
	          for (int i = 0; i < property1.size(); i++) {
	            if (!((Property) property1.get(i)).getAddress3().toLowerCase().equals(city.toLowerCase())) {
	              property1.remove(property1.get(i));
	            }
	          }
	        }
	      } else if (!address.isEmpty()) {
	        for (int i = 0; i < property1.size(); i++) {
	          if (!((Property) property1.get(i)).getAddress1().toLowerCase().equals(address.toLowerCase())) {
	            property1.remove(property1.get(i));
	          }
	        }
	      }
	    } else if (!city.isEmpty()) {
	      if (!address.isEmpty()) {
	        property1 = this.propRepo.findByAddress3AndAddress1(city.toLowerCase(), address.toLowerCase());
	      } else {
	        property1 = this.propRepo.findByAddress3(city.toLowerCase());
	      }
	    } else if (!address.isEmpty()) {
	      property1 = this.propRepo.findByAddress1(address.toLowerCase());
	    }
    }
    
    if (property1 != null) {

      // Checking to see if the property is 'listed' or 'unlisted'
      for (int i = 0; i < property1.size(); i++) {
        if (property1.get(i).getListed() == false) {
          property1.remove(i);
          i--;
        }
      }

      /*final Date startOfToday = Date.from(ZonedDateTime.now().with(LocalTime.MIN).toInstant());
      for (int i = 0; i < property1.size(); i++) {
        if (startOfToday.after(property1.get(i).getStartDate())
            && startOfToday.before(property1.get(i).getEndDate())) {
          property1.remove(i);
          i--;
        }
      }*/

      final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date searchStartDateFormat = null;
      Date searchEndDateFormat = null;

      if (!startDate.equals("")) {
        searchStartDateFormat = formatter.parse(startDate);
      }
      if (!endDate.equals("")) {
        searchEndDateFormat = formatter.parse(endDate);
      }

      for (int i = 0; i < property1.size(); i++) {
        final Date propertyStartDate = property1.get(i).getStartDate();
        final Date propertyEndDate = property1.get(i).getEndDate();

        if (searchStartDateFormat != null && (searchStartDateFormat.before(propertyStartDate)
            || searchStartDateFormat.after(propertyEndDate))) {
          property1.remove(i);
          i--;
        }
        if (searchEndDateFormat != null && (searchEndDateFormat.before(propertyStartDate)
            || searchEndDateFormat.after(propertyEndDate))) {
          property1.remove(i);
          i--;
        }
      }

      if (minPrice != 0) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getPricePerWeek() < minPrice) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (maxPrice != 0) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getPricePerWeek() > maxPrice) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (minNumRooms != 0) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getNumberOfRooms().intValue() < minNumRooms) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (maxNumRooms != 0) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getNumberOfRooms().intValue() > maxNumRooms) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (propertyType != -1) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getType().intValue() != propertyType) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (!occupantType.equals("null")) {
        for (int i = 0; i < property1.size(); i++) {
          if (!((Property) property1.get(i)).getOccupantType().equals(occupantType)) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (billsIncluded) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getBillsIncluded() != billsIncluded) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (petsAllowed) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getPetsAllowed() != petsAllowed) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (parking) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getParking() != parking) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (disabledAccess) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getDisabledAccess() != disabledAccess) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (smoking) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getSmoking() != smoking) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (quietArea) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getQuietArea() != quietArea) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (shortTerm) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getShortTerm() != shortTerm) {
            property1.remove(i);
            i--;
          }
        }
      }
      if (singleGender) {
        for (int i = 0; i < property1.size(); i++) {
          if (((Property) property1.get(i)).getSingleGender() != singleGender) {
            property1.remove(i);
            i--;
          }
        }
      }

      // Checking to see if the property has any available rooms
      for (int i = 0; i < property1.size(); i++) {
        if ((property1.get(i).getNumberOfRooms() - property1.get(i).getNumberOfOccupiedRooms()) < minNumRooms) {
          property1.remove(i);
          i--;
        }
      }
      
      indexStart = 1;
      indexFinish = 10;
      if (property1.size() < 10) {
        indexFinish = property1.size();
      }
      if (pageno > 0) {
        indexStart = (pageno * 10) + 1;
        indexFinish = indexStart + 9;
        if (indexFinish > property1.size()) {
          indexFinish = property1.size();
        }
      }

      pagecount = (property1.size() / 10) + 1;
      pageno = pageno + 1;
      
      propertyResults = property1.subList(indexStart - 1, indexFinish);
      
      final Iterable<Property> property2 = property1;

      final ArrayList<String> allPostcodes = new ArrayList<>();
      for (final Property prop : property2) {
        allPostcodes.add(prop.getPostcode());
      }

      final ArrayList<String> lats = new ArrayList<>();
      final ArrayList<String> lngs = new ArrayList<>();

      for (final String pstcde : allPostcodes) {
        final String callUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
            + pstcde.replaceAll("\\s", "");
        try {
          final String out = new Scanner(new URL(callUrl).openStream()).useDelimiter("\\A").next();
          final JSONObject json = new JSONObject(out);
          final JSONArray results = json.getJSONArray("results");
          final JSONObject location = results.getJSONObject(0);
          lats.add(location.getJSONObject("geometry").getJSONObject("location").getString("lat"));
          lngs.add(location.getJSONObject("geometry").getJSONObject("location").getString("lng"));
        } catch (final Exception e) {
          //e.printStackTrace();
        }
      } 

      model.addAttribute("lats", lats);
      model.addAttribute("lngs", lngs);

      model.addAttribute("postcodes", allPostcodes);
      
    } else {
      property1 = propRepo.findByUserId(-1);
      propertyResults = property1;
    }

    

    final ModelAndView modelAndView = new ModelAndView("property_results");
    modelAndView.addObject("listProperty", propertyResults);
    modelAndView.addObject("currentPage", pageno);
    modelAndView.addObject("pages", pagecount);

    return modelAndView;
  }

  /**
   * method return string for junit controller test.
   * 
   * @return string "search"
   */
  public String getTestMessage() {
    return "search";
  }

}
