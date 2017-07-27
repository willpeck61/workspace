package webservice.persistence.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import webservice.domain.Instance;
import webservice.domain.InstanceProperty;

public interface InstancePropertyRepository extends CrudRepository<InstanceProperty, Integer> {
  public Collection<InstanceProperty> findByInstance(Instance instance);
}
