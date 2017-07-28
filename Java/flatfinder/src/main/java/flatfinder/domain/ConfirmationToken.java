package flatfinder.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "confirmationToken")
public class ConfirmationToken {
	private static final int EXPIRATION = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="token")
	private String token;
	
	@Column(name="forUser")
	private String forUser;
	
	@Column(name="validUntil")
	private Date validUntil;
	
	@Column(name="used")
	private boolean used;
	
	@Column(name="tokenType")
	private String tokenType;
	
	public Integer getId() {
	    return id;
	}
	  
	public void setId(Integer id) {
	  this.id = id;
	}
	
	public String getToken(){
		return token;
	}
	
	public void setToken(String t){
		this.token = t;
	}
	
	public String getForUser(){
		return forUser;
	}
	
	public void setForUser(String u){
		this.forUser = u;
	}
	
	public Date getValidUntil(){
		return validUntil;
	}
	
	public void setValidUntil(Date d){
		this.validUntil = d;
	}
	
	public boolean getUsed(){
		return used;
	}
	
	public void setUsed(boolean us){
		this.used = us;
	}
	
	public String getType(){
		return tokenType;
	}
	
	public void setTokenType(String type){
		this.tokenType = type;
	}
	
    public ConfirmationToken(String token, String user) {
        super();
        this.token = token;
        this.forUser = user;
        this.validUntil = calculateExpiryDate(EXPIRATION);
        this.used = false;
    }
     
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
	
}
