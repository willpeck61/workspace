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
@Table(name = "message")
public class Message {
	
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(name = "timeSent")
  private String time = new Date().toString();

  @Column(name = "from_user")
  private String from;
  
  @Column(name = "to_receive")
  private String to;
  
  @Column(name = "message")
  private String message;
  
  @Column(name = "threadID")
  private Integer threadId;

  @Column(name = "conversation_id")
  private Integer conversationId;
  
  public void setId(Integer id){
	  this.id = id;
  }
  
  public Integer getId(){
	  return id;
  }

  public void setFrom(String from){
	  this.from = from;
  }
  
  public String getFrom(){
	  return from;
  }
  
  public void setTo(String to){
	  this.to = to;
  }
  
  public String getTo(){
	  return to;
  }
  
  public void setMessage(String message){
	  this.message = message;
  }
  
  public String getMessage(){
	  return message;
  }
  
  public void setTime(String time){
	  this.time = time;
  }
  
  public String getTime(){
	  return time;
  }
 
  public Integer getThreadId(){
	  return threadId;
  }
  
  public void setThreadId(Integer threadId){
	  this.threadId = threadId;
  }
  
  public Integer getConversationId(){
	  return conversationId;
  }
  
  public void setConversationId(Integer conversationId){
	  this.conversationId = conversationId;
  }
  
}
