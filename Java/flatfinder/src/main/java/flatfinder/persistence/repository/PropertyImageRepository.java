package flatfinder.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Property;
import flatfinder.domain.PropertyImage;

public interface PropertyImageRepository extends CrudRepository<PropertyImage, Integer> {
  public PropertyImage findById(Integer id);
}