package webservice.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Instance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	private Classnode classnodes;

	@OneToMany(mappedBy = "property")
	private Collection<InstanceProperty> properties;
	
	@Column(unique = true)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Classnode getClassnodes() {
		return classnodes;
	}

	public void setClassnodes(Classnode classnodes) {
		this.classnodes = classnodes;
	}

	public Collection<InstanceProperty> getProperties() {
		return properties;
	}

	public void setProperties(Collection<InstanceProperty> properties) {
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
