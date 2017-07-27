package webservice.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Datatype {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(unique = true)
  private String name;
  
  private Boolean isClass;
  
  @OneToMany(
      cascade = CascadeType.ALL)
  @JoinTable(name = "property_datatype", 
      joinColumns = {@JoinColumn(name = "datatype_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "property_id", referencedColumnName = "id")})
  private Set<Property> properties;
  
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

  public Set<Property> getProperties() {
    return properties;
  }

  public void setProperties(Set<Property> properties) {
    this.properties = properties;
  }

  public Boolean getIsClass() {
    return isClass;
  }

  public void setIsClass(Boolean isClass) {
    this.isClass = isClass;
  }
}
