package flatfinder.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "room")
public class Room {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "room_id")
  private Integer id;
  
  @Column(name = "property_id")
  private Integer propertyId;
  
  @Column(name = "furnished")
  private Boolean furnished;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_created")
  private Date date = new Date();
  
  @Column(name = "headline_text")
  private String headline;
  
  @Column(name = "description")
  private String description;
  
  @Column(name = "ensuite")
  private boolean ensuite;
  
  @Column(name = "occupiedByUsername")
  private String occupiedByUsername;
  
  @Column(name = "width")
  private Integer width;
  
  @Column(name = "height")
  private Integer height;
  
  @Column(name = "double_room")
  private boolean doubleRoom;
  
  public void setPropertyId(Integer propertyId){
	  this.propertyId = propertyId;
  }
  
  public Integer getPropertyId(){
	  return this.propertyId;
  }
  
  public boolean getDoubleRoom() {
    return this.doubleRoom;
  }
  
  public void setDoubleRoom(boolean doubleRoom) {
    this.doubleRoom = doubleRoom;
  }
  
  public String getOccupiedByUsername() {
    return this.occupiedByUsername;
  }
  
  public void setOccupiedByUsername(String occupiedByUsername) {
    this.occupiedByUsername = occupiedByUsername;
  }
  
  public Integer getWidth() {
    return this.width;
  }
  
  public void setWidth(Integer width) {
    this.width = width;
  }
  
  public Integer getHeight() {
    return this.height;
  }
  
  public void setHeight(Integer height) {
    this.height = height;
  }
  
  public boolean getEnsuite() {
    return ensuite;
  }
  
  public void setEnsuite(boolean ensuite) {
    this.ensuite = ensuite;
  }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Boolean getFurnished() {
    return furnished;
  }
  
  public void setFurnished(Boolean furnished) {
    this.furnished = furnished;
  }
  
  public void setDateCreated() {
    date = new Timestamp(date.getTime());
  }
  
  public Date getDateCreated() {
    return date;
  }
  
  public String getHeadline() {
    return headline;
  }
  
  public void setHeadline(String headline) {
    this.headline = headline;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
}

