package sciConsole.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="dependancy")
public class Dependancy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dependancy_id")
	private Integer id;
	private String name;
	private String description;
	private String path;
	@OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name="dependancy_list",
        joinColumns = {@JoinColumn(name="dependancy_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="trunk_id", referencedColumnName="id")}
    )
	public Integer getId() {
        return id;
    }
	public void setId(Integer id){
        this.id = id;
    }
	
	public String getDepName(){
		return name;
	}
	public void setDepName(String name){
		this.name = name;
	}
	
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path = path;
	}
}
