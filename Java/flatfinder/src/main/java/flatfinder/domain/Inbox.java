package flatfinder.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userInbox")
public class Inbox {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "sender")
	private String sender;
	
	@Column(name = "receiver")
	private String receiver;
	
	@Column(name = "creation")
	private String creation;
	
	@Column(name = "message")
	private String message;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getSender(){
		return sender;
	}
	
	public void setSender(String f){
		this.sender = f;
	}
	
	public String getReceiver(){
		return receiver;
	}
	
	public void setReceiver(String t){
		this.receiver = t;
	}
	
	public String getCreation(){
		return creation;
	}
	
	public void setCreation(String c){
		if(c.equals("now")){
		  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		  Date date = new Date();
		  this.creation = dateFormat.format(date);
		} else {
			this.creation = c;
		}
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String m){
		this.message = m;
	}
	
}
