package sciConsole.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sci")
public class Sci {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="sci_id")
	private Integer id;
	private String name;
	
	@OneToMany
	@JoinColumn(name="sci_id")
	
	private Set <Ver> versions;
	private String sciDesc;
	
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
	public void setSciDesc(String sciDesc){
		this.sciDesc = sciDesc;
	}
	public String getSciDesc(){
		return sciDesc;
	}
}
