package webservice.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import webservice.domain.Property;

public interface PropertyRepository extends CrudRepository<Property, Integer> {
  public Property findByName (String name);
}
