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
@Table(name = "property_feedback")
public class PropertyFeedback {
	
	
	
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(name = "property_id")
  private Integer propertyId;
  
  @Column(name = "user_name")
  private String userName;
  
  @Column(name = "comment")
  private String comment;
  
  @Column(name = "rating")
  private Integer rating;
  
  @Column(name = "approved")
  private Boolean approved;
  
  @Column(name = "created")
  private String created;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getPropertyId() {
    return this.propertyId;
  }
  
  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }
  
  public String getUserName() {
	    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getComment() {
    return comment;
  }
  
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public Integer getRating() {
    return this.rating;
  }
  
  public void setRating(Integer rating) {
    this.rating = rating;
  }
  
  public Boolean getApproved() {
    return this.approved;
  }
  
  public void setApproved(Boolean approved) {
    this.approved = approved;
  }
  
  public void setCreated(String c){
	  if(c.equals("now")){
		  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		  Date date = new Date();
		  this.created = dateFormat.format(date);
	  } else {
		  this.created = c;
	  }
  }
  
  public String getCreated(){
	  return created;
  }
  
}
