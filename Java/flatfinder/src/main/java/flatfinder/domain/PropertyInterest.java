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
@Table(name = "propertyInterest")
public class PropertyInterest {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(name = "propertyId")
  private Integer propertyId;
  
  @Column(name = "userId")
  private Integer userId;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_created")
  private Date date = new Date();
  
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
}
