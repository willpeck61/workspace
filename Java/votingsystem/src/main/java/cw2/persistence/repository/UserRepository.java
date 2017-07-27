package cw2.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import cw2.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
  public User findById(Integer id);
  public User findByUsername(String username);
  public User findByEmail(String email);
}
