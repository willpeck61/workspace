package flatfinder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "property_image")
public class PropertyImage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "propertyimage_id")
  private Integer id;
  
  @Column(name = "property_id")
  private Integer propertyId;
  
  @Column(name = "image_link")
  private String imageLink;
  
  @Column(name = "title")
  private String title;
  
  @Column(name = "description")
  private String description;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getPropertyId() {
    return this.propertyId;
  }
  
  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }
  
  public String getImageLink() {
    return this.imageLink;
  }
  
  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
}
