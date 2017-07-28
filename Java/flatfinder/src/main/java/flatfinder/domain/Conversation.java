package flatfinder.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conversation")
public class Conversation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "numberOfMessages")
	private Integer numberOfMessages;
	
	@Column(name = "lastReceived")
	private String lastReceived;
	
	@Column(name = "sender")
	private String sender;
	
	@Column(name = "recipient")
	private String recipient;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getNumberOfMessages(){
		return numberOfMessages;
	}
	
	public void setNumberOfMessages(Integer num){
		this.numberOfMessages = num;
	}
	
	public String getLastReceived(){
		return lastReceived;
	}
	
	public void setLastReceived(String last){
		this.lastReceived = last;
	}
	
	public String getSender(){
		return sender;
	}
	
	public void setSender(String sender){
		this.sender = sender;
	}
	
	public String getRecipient(){
		return recipient;
	}
	
	public void setRecipient(String recipient){
		this.recipient = recipient;
	}
	
}
