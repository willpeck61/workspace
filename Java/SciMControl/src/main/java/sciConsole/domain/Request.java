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
@Table(name="request")
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "request_id")
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date date = new Date();
	@Column(name = "version_id")
	private Integer versionId;
	@Column(name = "sci_id")
	private Integer sciId;
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "assigned_to")
	private Integer type;
	private String reqDesc;
	private String reqSolution;
	private Integer status;
	private Date dueDate;
	private String priority;
	private Integer assignTo;
	private String rejectReason;
	private Date dateComplete;
	
	public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
	public void setSciId(Integer sciId){
		this.sciId = sciId;
	}
	public int getSciId(){
		return sciId;
	}
	public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }
	public Integer getVersionId(){
		return versionId;
	}
	public void setAssignTo(Integer assignTo){
		this.assignTo = assignTo;
	}
	public Integer getAssignTo(){
		return assignTo;
	}
	public void setCreatedBy(Integer createdBy){
		this.createdBy = createdBy;
	}
	public Integer getCreatedBy(){
		return createdBy;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public Integer getType(){
		return type;
	}
	public void setReqDesc(String reqDesc){
		this.reqDesc = reqDesc;
	}
	public String getReqDesc(){
		return reqDesc;
	}
	public void setReqSolution(String reqSolution){
		this.reqSolution = reqSolution;
	}
	public String getReqSolution(){
		return reqSolution;
	}
	public void DateCreated(){
		date = new Timestamp(date.getTime());
	}
	public Date getDateCreated(){
		return date;
	}
	public void setDueDate(Date dueDate){
		this.dueDate = dueDate;
	}
	public Date getDueDate(){
		return dueDate;
	}
	public void setDateComplete(Date dateComplete){
		this.dateComplete = dateComplete;
	}
	public Date getDateComplete(){
		return dateComplete;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public int getStatus(){
		return status;
	}
	public void setPriority(String priority){
		this.priority = priority;
	}
	public String getPriority(){
		return priority;
	}
	public void setRejectReason(String rejectReason){
		this.rejectReason = rejectReason;
	}
	public String getRejectReason(){
		return rejectReason;
	}
}
