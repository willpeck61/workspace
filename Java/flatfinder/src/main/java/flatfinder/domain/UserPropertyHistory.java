package flatfinder.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "UserPropertyHistory")
public class UserPropertyHistory {
  
	
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
	
  @Column(name = "userId")
  private Integer userId;
  
  @Column(name = "propertyId")
  private Integer propertyId;
  
  @Column(name = "numViews")
  private Integer numViews;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_viewed")
  private Date dateViewed = new Date();
  
  public Integer getId(){
	  return id;
  }
  
  public Integer getPropertyId() {
    return propertyId;
  }
  
  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }
  
  public Integer getUserId() {
    return userId;
  }
  
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
  
  public void setNumViews(Integer numViews) {
	  this.numViews = numViews;
  }
  
  public Integer getNumViews(){
	  return numViews;
  }
  
  public void setDateViewed(){
	  this.dateViewed = new Date();
  }
  
  public Date getDateViewed(){
	  return dateViewed;
  }
}