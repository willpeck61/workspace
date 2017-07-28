package sciConsole.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="trunk")
public class Trunk {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trunk_id")
	private Integer id;
	@Column(name = "sci_id")
	private Integer sciid;
	@Column(name = "version_id")
	private Integer verid;
	@Column(name = "branch_id")
	private Integer branchId;
	private Integer fileIndexId;
	@OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles", 
        joinColumns = {@JoinColumn(name="trunk_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="dependancy_id", referencedColumnName="id")}
    )
	public Integer getId() {
        return id;
    }
	public void setId(Integer id){
        this.id = id;
    }
	
	public Integer getSciId(){
		return sciid;
	}
	public void setSciId(Integer sciid){
		this.sciid = sciid;
	}
	
	public Integer getVersionId(){
		return verid;
	}
	public void setVersionId(Integer verid){
		this.verid = verid;
	}
	
	public Integer getFileIndexId(){
		return fileIndexId;
	}
	public void setFileIndexId(Integer fileIndexId){
		this.fileIndexId = fileIndexId;
	}
	
	public Integer getBranchId(){
		return branchId;
	}
	public void setBranchId(Integer branchId){
		this.branchId = branchId;
	}
}
