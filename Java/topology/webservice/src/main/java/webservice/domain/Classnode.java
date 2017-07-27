package webservice.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Classnode {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(unique = true)
  private String name;
  
  private Boolean root;
  
  @ManyToMany( targetEntity = Property.class)
  @JoinTable(
      name = "Classnode_Property",
      joinColumns = @JoinColumn(name = "classnode_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "property_id", referencedColumnName = "id"))
  private Collection<Property> properties;
  
  @ManyToMany(
      cascade = CascadeType.REMOVE,
      mappedBy = "parents")
  private Collection<Classnode> children;
  
  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<Classnode> parents;
 
  @OneToMany
  @JoinTable(
      name = "Classnode_Instance",
      joinColumns = @JoinColumn(name = "classnode_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "instance_id", referencedColumnName = "id"))
  private Collection<Instance> instances;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(unique = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<Classnode> getParents() {
    return parents;
  }

  public void setParents(Collection<Classnode> parents) {
    this.parents = parents;
  }

  public Collection<Classnode> getChildren() {
    return children;
  }

  public void setChildren(Collection<Classnode> children) {
    this.children = children;
  }

  public Collection<Property> getProperties() {
    return properties;
  }

  public void setProperties(Collection<Property> properties) {
    this.properties = properties;
  }

  public Boolean getRoot() {
    return root;
  }

  public void setRoot(Boolean root) {
    this.root = root;
  }

  public Collection<Instance> getInstances() {
    return instances;
  }

  public void setInstances(Collection<Instance> instances) {
    this.instances = instances;
  }
}
