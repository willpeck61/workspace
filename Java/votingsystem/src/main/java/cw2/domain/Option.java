package cw2.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Option {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @ManyToOne(optional=false)
  @JoinColumn(name="question_id",
    referencedColumnName="id")
  private Question question;
  
  @OneToMany(mappedBy = "option",
      targetEntity=Vote.class, 
        fetch=FetchType.EAGER)
  private Set<Vote> votes;
  
  private String option;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public Set<Vote> getVotes() {
    return votes;
  }

  public void setVotes(Set<Vote> votes) {
    this.votes = votes;
  }
}
