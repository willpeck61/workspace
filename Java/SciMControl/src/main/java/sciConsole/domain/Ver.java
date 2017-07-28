package sciConsole.domain;

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
@Table(name="version")
public class Ver{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "version_id")
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date date = new Date();
	@Column(name="sci_id")
	private Integer sciId;
	@Column(name = "created_by")
	private Integer createdBy;
	private String name;
	private String verDesc;
	private Integer status;
	
	public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
	public String getName() {
        return name;
    }
	public void setName(String name) {
        this.name = name;
    }
	public Integer getSciId(){
		return sciId;
	}
	public void setSciId(Integer sciId){
		this.sciId = sciId;
	}
	public String getVerDesc(){
		return verDesc;
	}
	public void setVerDesc(String verDesc){
		this.verDesc = verDesc;
	}
	public void setCreatedBy(Integer createdBy){
		this.createdBy = createdBy;
	}
	public int getCreatedBy(){
		return createdBy;
	}
	public void setDateCreated(){
		date = new Timestamp(date.getTime());
	}
	public Date getDateCreated(){
		return date;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public int getStatus(){
		return status;
	}
}


