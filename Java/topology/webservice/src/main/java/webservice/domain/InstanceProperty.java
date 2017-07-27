package webservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InstanceProperty {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @ManyToOne
  @JoinColumn(name = "instance_id")
  private Instance instance;
  
  @ManyToOne
  @JoinColumn(name = "property_id")
  private Property property;
  
  private String value;

  public Instance getInstance() {
    return instance;
  }

  public void setInstance(Instance instance) {
    this.instance = instance;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
