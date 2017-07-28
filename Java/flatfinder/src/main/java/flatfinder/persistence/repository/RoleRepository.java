package flatfinder.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
  public Role findById(Integer id);
  public Role findByRole(String role);
}
