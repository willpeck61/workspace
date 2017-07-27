package cw2.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Question {
 
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @OneToMany(mappedBy = "question", 
    targetEntity=Vote.class, 
      fetch=FetchType.EAGER)
  private Set<Vote> vote;
  
  @OneToMany(mappedBy = "question",
      targetEntity=SecurityCode.class, 
        fetch=FetchType.EAGER)
  private Set<SecurityCode> seccode;
  
  @OneToMany(mappedBy = "question",
      targetEntity=Option.class, 
        fetch=FetchType.EAGER)
  private Set<Option> options;
  private String question;
  private Date expires;
  private Boolean active;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Set<Vote> getVote() {
    return vote;
  }

  public void setVote(Set<Vote> vote) {
    this.vote = vote;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
  
  @Temporal(TemporalType.DATE)
  public Date getExpires() {
    return expires;
  }

  public void setExpires(Date date) {
    this.expires = date;
  }

  public Set<SecurityCode> getSeccode() {
    return seccode;
  }

  public void setSeccode(Set<SecurityCode> seccode) {
    this.seccode = seccode;
  }
}