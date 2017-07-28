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
@Table(name = "property")
public class Property {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  //@Column(name = "property_id")
  private Integer id;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_created")
  private Date date = new Date();
  
  @Column(name = "owner_id")
  private Integer userId;
  
  @Column(name = "price_per_week")
  private float pricePerWeek;
  
  @Column(name = "no_of_rooms")
  private Integer numberOfRooms;
  
  @Column(name = "occupied_rooms")
  private Integer numberOfOccupiedRooms;
  
  @Column(name = "house_num")
  private String houseNum;
  
  @Column(name = "address_1")
  private String address1;
  
  @Column(name = "address_2")
  private String address2;
  
  @Column(name = "address_3")
  private String address3;
  
  @Column(name = "postcode")
  private String postcode;
  
  @Column(name = "no_of_views")
  private Integer numViews = 0;
  
  @Column(name = "available_from")
  private Date startDate;
  
  @Column(name = "available_to")
  private Date endDate;
  
  @Column(name = "property_type")
  private Integer type;
  
  @Column(name = "headline_text")
  private String headline;
  
  @Column(name = "description")
  private String description;
  
  @Column(name = "localArea")
  private String localArea;
  
  @Column(name = "bills_included")
  private boolean billsIncluded;
  
  @Column(name = "pets_allowed")
  private boolean petsAllowed;
  
  @Column(name = "parking")
  private boolean parking;
  
  @Column(name = "disabled_access")
  private boolean disabledAccess;
  
  @Column(name = "smoking")
  private boolean smoking;
  
  @Column(name = "quiet_area")
  private boolean quietArea;
  
  @Column(name = "short_term")
  private boolean shortTerm;
  
  @Column(name = "occupant_type")
  private String occupantType;
  
  @Column(name = "single_gender")
  private boolean singleGender;
  
  @Column(name = "listed")
  private boolean listed;
  
  @Column(name = "num_interests")
  private Integer numInterests = 0;
  
  public boolean getSingleGender() {
    return singleGender;
  }
  
  public void setSingleGender(boolean singleGender) {
    this.singleGender = singleGender;
  }
  
  public String getOccupantType() {
    return occupantType;
  }
  
  public void setOccupantType(String occupantType) {
    this.occupantType = occupantType;
  }
  
  public boolean getShortTerm() {
    return shortTerm;
  }
  
  public void setShortTerm(boolean shortTerm) {
    this.shortTerm = shortTerm;
  }
  
  public boolean getQuietArea() {
    return quietArea;
  }
  
  public void setQuietArea(boolean quietArea) {
    this.quietArea = quietArea;
  }
  
  public boolean getSmoking() {
    return smoking;
  }
  
  public void setSmoking(boolean smoking) {
    this.smoking = smoking;
  }
  
  public boolean getDisabledAccess() {
    return disabledAccess;
  }
  
  public void setDisabledAccess(boolean disabledAccess) {
    this.disabledAccess = disabledAccess;
  }
  
  public boolean getParking() {
    return parking;
  }
  
  public void setParking(boolean parking) {
    this.parking = parking;
  }
  
  public boolean getPetsAllowed() {
    return petsAllowed;
  }
  
  public void setPetsAllowed(boolean petsAllowed) {
    this.petsAllowed = petsAllowed;
  }
  
  public boolean getBillsIncluded() {
    return billsIncluded;
  }
  
  public void setBillsIncluded(boolean billsIncluded) {
    this.billsIncluded = billsIncluded;
  }
  
  public Date getDate() {
    return date;
  }
  
  public float getPricePerWeek() {
    return pricePerWeek;
  }
  
  public void setPricePerWeek(float pricePerWeek) {
    this.pricePerWeek = pricePerWeek;
  }
  
  public Integer getNumberOfRooms() {
    return numberOfRooms;
  }
  
  public void setNumberOfRooms(Integer numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
  }
  
  public Integer getNumberOfOccupiedRooms(){
	  return this.numberOfOccupiedRooms;
  }
  

  public void setNumberOfOccupiedRooms(Integer numberOfOccupiedRooms){
	  this.numberOfOccupiedRooms = numberOfOccupiedRooms;
  }
  
  public String getHouseNum(){
	  return houseNum;
  }
  
  public void setHouseNum(String houseNum){
	  this.houseNum = houseNum;
  }
  
  public String getAddress1() {
    return address1;
  }
  
  public void setAddress1(String address1) {
    this.address1 = address1;
  }
  
  public String getAddress2() {
    return address2;
  }
  
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  
  public String getAddress3() {
    return address3;
  }
  
  public void setAddress3(String address3) {
    this.address3 = address3;
  }
  
  public String getPostcode() {
    return postcode;
  }
  
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }
  
  public Integer getNumViews() {
    return numViews;
  }
  
  public void setNumViews(Integer numViews) {
    this.numViews = numViews;
  }
  
  public Date getStartDate() {
    return startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Date getEndDate() {
    return endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public void setCreatedBy(Integer userId) {
    this.userId = userId;
  }
  
  public int getCreatedBy() {
	return userId;
  }
  

  public Date getDateCreated() {
    return date;
  }
  
  public Integer getType() {
    return type;
  }
  
  public void setType(Integer type) {
    this.type = type;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String desc) {
    this.description = desc;
  }
  
  public String getLocalArea() {
	 return localArea;
  }
	  
  public void setLocalArea(String localAreaDesc) {
	 this.localArea = localAreaDesc;
  }
  
  public void setListed(boolean listed){
	  this.listed = listed;
  }
  
  public boolean getListed(){
	  return listed;
  }
  
  public String getHeadline() {
    return headline;
  }
  
  public void setHeadline_text(String headline) {
    this.headline = headline;
  }
  
  public void setNumInterests(Integer interest){
	  this.numInterests = interest;
  }
  
  public Integer getNumInterests(){
	  return numInterests;
  }
  
}
