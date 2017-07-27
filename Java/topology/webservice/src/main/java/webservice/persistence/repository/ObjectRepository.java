package webservice.persistence.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import webservice.domain.Classnode;
import webservice.domain.Instance;

public interface ObjectRepository extends CrudRepository<Instance, Integer> {
 public Collection<Instance> findByClassnodes(Collection<Classnode> classnode);
}
