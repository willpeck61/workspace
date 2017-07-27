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
public class Vote {
 
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @OneToOne(optional = true)
  @JoinTable(
      name = "vote_user",
      joinColumns = @JoinColumn(name = "vote_id",
      referencedColumnName = "id"),
      inverseJoinColumns = 
      @JoinColumn(name = "user_id", referencedColumnName = "id"))
  private User user;
  
  @ManyToOne(optional=false)
  @JoinColumn(name="question_id",
      referencedColumnName="id")
  private Question question;
  
  @ManyToOne(optional=false)
  @JoinColumn(name="option_id",
      referencedColumnName="id")
  private Option option;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Option getOption() {
    return option;
  }

  public void setOption(Option option) {
    this.option = option;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
