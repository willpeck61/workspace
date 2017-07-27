package cw2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class SecurityCode {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String securitycode;
  private Boolean inuse;
  
  @ManyToOne(optional=false)
  @JoinColumn(name="question_id",
    referencedColumnName="id")
  private Question question;
  
  @OneToOne(optional = true)
  @JoinTable(
      name = "secure_user",
      joinColumns = @JoinColumn(name = "secure_id",
      referencedColumnName = "id"),
      inverseJoinColumns = 
      @JoinColumn(name = "user_id", referencedColumnName = "id"))
  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getSecuritycode() {
    return securitycode;
  }

  public void setSecuritycode(String securitycode) {
    this.securitycode = securitycode;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getInuse() {
    return inuse;
  }

  public void setInuse(Boolean inuse) {
    this.inuse = inuse;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
