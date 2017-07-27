package webservice.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Property {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  @OneToMany
 (mappedBy = "instance")
  private Collection<InstanceProperty> instance;
  
  @ManyToMany
  (targetEntity = Classnode.class)
  private Collection<Classnode> classnodes;
  
  @OneToOne(
      optional = true,
      cascade = CascadeType.ALL)
  @JoinTable(
      name = "property_datatype",
      joinColumns = {
              @JoinColumn (name = "property_id",
                      referencedColumnName = "id")},
      inverseJoinColumns = {
              @JoinColumn(name = "datatype_id",
                      referencedColumnName = "id")})
  private Datatype datatype;
  
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

  public Datatype getDatatype() {
    return datatype;
  }

  public void setDatatype(Datatype datatype) {
    this.datatype = datatype;
  }

  public Collection<InstanceProperty> getInstance() {
    return instance;
  }

  public void setInstance(Collection<InstanceProperty> instance) {
    this.instance = instance;
  }

  public Collection<Classnode> getClassnode() {
    return classnodes;
  }

  public void setClassnode(Collection<Classnode> classnodes) {
    this.classnodes = classnodes;
  }
}
