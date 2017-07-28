package flatfinder.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

@Entity
@Table(name = "users")
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(name = "login")
  private String login;
  
  @Column(name = "password")
  private String password;
  
  @Column(name = "first_name")
  private String firstName;
  
  @Column(name = "last_name")
  private String lastName;
  
  @Column(name = "address_1")
  private String address1;
  
  @Column(name = "address_2")
  private String address2;
  
  @Column(name = "address_3")
  private String address3;
  
  @Column(name = "postcode")
  private String postcode;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "gender")
  private Integer gender;
  
  @Column(name = "age_group")
  private Integer ageGroup;
  
  @Column(name = "employment_status")
  private Integer status;
  
  @Column(name = "buddy_up")
  private Integer buddyUp;
  
  @Column(name = "title")
  private String title;
  
  @Column(name = "hits")
  private Integer hits;
  
  @Column(name = "secret")
  private String secret;
  
  @Column(name = "answer")
  private String answer;
  
  
  @Column(name="account_status")
  private String accountStatus = "active";
  
  private Date dateCreated;
  
  @Column(name="lastlogin", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date lastActive;

  public Date getLastActive() {
    return lastActive;
  }

  @OneToOne(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_roles",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Role role;
  
  private Integer oldRole;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getLogin() {
    return login;
  }
  
  public void setLogin(String login) {
    this.login = login;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getLastName() {
    return lastName;
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
  
  public Integer getGender() {
    return gender;
  }
  
  public void setGender(Integer gender) {
    this.gender = gender;
  }
  
  public Integer getAgeGroup() {
    return ageGroup;
  }
  
  public void setAgeGroup(Integer ageGroup) {
    this.ageGroup = ageGroup;
  }
  
  public Integer getStatus() {
    return status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Integer getBuddyUp() {
    return buddyUp;
  }
  
  public void setLastActive(Long ms) {
    if (null == ms){
      Date date = new Date();
      this.lastActive = new Timestamp(date.getTime());
    } else {
      Date date = new Date();
      this.lastActive = new Timestamp(date.getTime() - ms);
    }
  }

  public void setBuddyUp(Integer buddyUp) {
    this.buddyUp = buddyUp;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Role getRole() {
    return role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setHits(){
	  this.hits += 1;
  }
  
  public Integer getHits(){
	  return hits;
  }
  
  public void setAccountStatus(String st){
	  this.accountStatus = st;
  }
  
  public String getAccountStatus(){
	  return accountStatus;
  }

  public Integer getOldRole() {
    return oldRole;
  }

  public void setOldRole(Integer oldRole) {
    this.oldRole = oldRole;
  }
  
  public void setDateCreated(){
      Date date = new Date();
      this.dateCreated = new Timestamp(date.getTime());
  }

  public Date getDateCreated() {
    return dateCreated;
  }
  
  public void setSecret(String s){
	  this.secret = s;
  }
  
  public String getSecret(){
	  return secret;
  }
  
  public void setAnswer(String a){
	  this.answer= a;
  }
  
  public String getAnswer(){
	  return answer;
  }
  
}
