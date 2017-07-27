package webservice.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import webservice.domain.Instance;

public interface InstanceRepository extends CrudRepository<Instance, Integer> {
  public Instance findByName(String name);
}
