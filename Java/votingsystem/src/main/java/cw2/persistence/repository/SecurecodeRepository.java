package cw2.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import cw2.domain.SecurityCode;
import cw2.domain.User;

public interface SecurecodeRepository extends CrudRepository<SecurityCode, Integer>{
  public SecurityCode findByUser(User user);
  public SecurityCode findBySecuritycode(String code);
}
