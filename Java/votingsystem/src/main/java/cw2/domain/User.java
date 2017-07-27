package cw2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String username;
  private String password;
  private Integer role;
  private Boolean active;
  private String title;
  private String firstname;
  private String lastname;
  private String address1;
  private String address2;
  private String address3;
  private String postcode;
  private String email;
  private String phone;
  private Boolean token;

  /*
   * @OneToOne(optional = true)
   * 
   * @JoinTable(name = "secure_user", joinColumns = @JoinColumn(name =
   * "user_id", referencedColumnName = "id"), inverseJoinColumns
   * = @JoinColumn(name = "secure_id", referencedColumnName = "id")) private
   * SecurityCode securitycode;
   */

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Boolean getToken() {
    return token;
  }

  public void setToken(Boolean token) {
    this.token = token;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /*
   * public SecurityCode getSecuritycode() { return securitycode; }
   * 
   * public void setSecuritycode(SecurityCode securitycode) { this.securitycode
   * = securitycode; }
   */
}
