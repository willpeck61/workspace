package flatfinder.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import flatfinder.domain.Role;
import flatfinder.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
  public User findByLogin(String login);
  public User findById(Integer id);
  public List<User> findByBuddyUpAndRole(Integer buddyup, Role role);
  public User findByEmail(String email);
  public List<User> findByFirstNameOrLastName(String querystr1, String querystr2 );
}
