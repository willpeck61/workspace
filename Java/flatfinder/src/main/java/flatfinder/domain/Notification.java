package flatfinder.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Integer id;
	  
	  @Column(name = "forUser")
	  private String forUser;
	  
	  @Column(name = "notificationText")
	  private String notificationText;
	  
	  @Column(name = "isSeen")
	  private boolean isSeen = false;
	  
	  @Column(name = "whenCreated")
	  private String whenCreated;
	  
	  @Column(name = "notificationType")
	  private String notificationType;
	  
	  @Column(name = "redirUrl")
	  private String redirUrl;
	  
	  @Column(name = "property_postcode")
	  private String propertyPostcode;
	  
	  private Integer requestId;
	  
	  private String createdBy;
	  
	  public String getCreatedBy() {
      return createdBy;
    }

    public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
    }

    public void setId(Integer Id){
		  this.id = Id;
	  }
	  
	  public Integer getId(){
		  return id;
	  }
	  
	  public void setForUser(String forUser){
		  this.forUser = forUser;
	  }
	  
	  public String getForUser(){
		  return forUser;
	  }
	  
	  public void setNotificationText(String text){
		  this.notificationText = text;
	  }
	  
	  public String getNotificationText(){
		  return notificationText;
	  }
	  
	  public void setIsSeen(boolean status){
		  this.isSeen = status;
	  }
	  
	  public boolean getIsSeen(){
		  return isSeen;
	  }
	  
	  public void setWhenCreated(String created){
		  if(created.equals("today")){
			  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			  Date date = new Date();
			  this.whenCreated = dateFormat.format(date);
		  } else {
			  this.whenCreated = created;
		  }
	  }
	  
	  public String getWhenCreated(){
		  return whenCreated;
	  }
	  
	  public void setNotificationType(String type){
		  this.notificationType = type;
	  }
	  
	  public String getNotificationType(){
		  return notificationType;
	  }
	  
	  public void setRedirUrl(String url){
		  this.redirUrl = url;
	  }
	  
	  public String getRedirUrl(){
		  return redirUrl;
	  }

    public Integer getRequestId() {
      return requestId;
    }

    public void setRequestId(Integer requestId) {
      this.requestId = requestId;
    }
    
    public String getPropertyPostcode(){
    	return propertyPostcode;
    }
    
    public void setPropertyPostcode(String propertyPostcode){
    	this.propertyPostcode = propertyPostcode;
    }

}
