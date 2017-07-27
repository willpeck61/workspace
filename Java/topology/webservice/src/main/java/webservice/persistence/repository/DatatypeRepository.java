package webservice.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import webservice.domain.Datatype;

public interface DatatypeRepository extends CrudRepository<Datatype, Integer> {
  public Datatype findByName(String name);
}
