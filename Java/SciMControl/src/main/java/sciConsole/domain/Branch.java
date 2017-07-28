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
@Table(name="branch")
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "request_id")
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date date = new Date();
	@Column(name = "version_id")
	private Integer versionid;
	@Column(name = "created_by")
	private Integer userId;
	@Column(name = "assigned_to")
	private Integer assignId;
	private Integer sciId;
	private String name;
	private String fileIndex;
	private Integer status;
	private Date lastEdited;
	private Integer lastUser;
	
	public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
	public int getReqId(){
        return id;
    }
	public void setSciId(Integer sciId){
		this.sciId = sciId;
	}
	public Integer getSciId(){
		return sciId;
	}
	public void setVersionId(int versionid) {
        this.versionid = versionid;
    }
	public Integer getVersionId(){
		return versionid;
	}
	
	public void setCreatedBy(Integer userId){
		this.userId = userId;
	}
	public void DateCreated(){
		date = new Timestamp(date.getTime());
	}
	public Date getDateCreated(){
		return date;
	}
	public void setLastEdited(Date lastEdited, Integer lastUser){
		this.lastEdited = lastEdited;
		this.lastUser = lastUser;
	}
	public Date getLastEdited(){
		return lastEdited;
	}
	public Integer getLastUser(){
		return lastUser;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public int getStatus(){
		return status;
	}
	public void setBranchName(String name){
		this.name = name;
	}
	public String getBranchName(){
		return name;
	}
	public void setFileIndex(String fileIndex){
		this.fileIndex = fileIndex;
	}
	public String getFileIndex(){
		return fileIndex;
	}
}
