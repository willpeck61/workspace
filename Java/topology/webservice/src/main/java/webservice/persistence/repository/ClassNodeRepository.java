package webservice.persistence.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import webservice.domain.Classnode;

public interface ClassNodeRepository extends CrudRepository<Classnode, Integer> {
  public Classnode findByName(String classname);
  public Collection<Classnode> findByChildrenIn(Collection<Classnode> child);
  public Collection<Classnode> findByParentsIn(Collection<Classnode> parents);
  public Classnode findByRoot(Boolean root);
}
