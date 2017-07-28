package flatfinder.domain;

import java.sql.Timestamp;
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
@Table(name = "buddyup")
public class BuddyUp {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "buddyup_id")
  private Integer id;
  
  @Column(name = "initialiser_id")
  private Integer initialiserId;
  
  @Column(name = "responder_id")
  private Integer responderId;
  
  @Column(name = "confirmed")
  private Integer confirmed;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getInitialiserId() {
    return initialiserId;
  }
  
  public void setInitialiserId(Integer initId) {
    this.initialiserId = initId;
  }
  
  public Integer getResponderId() {
    return responderId;
  }
  
  public void setResponderId(Integer responderId) {
    this.responderId = responderId;
  }
  
  public Integer getConfirmed() {
    return confirmed;
  }
  
  public void setConfirmed(Integer confirmed) {
    this.confirmed = confirmed;
  }
}
