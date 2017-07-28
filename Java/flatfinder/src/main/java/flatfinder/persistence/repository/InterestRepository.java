package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Interest;

public interface InterestRepository extends CrudRepository<Interest, Integer> {
  public Interest findById(Integer id);
  public Interest findByInterestAndUserId(String interest, Integer userid);
  public List<Interest> findByUserId(Integer userid);
  public List<Interest> findByInterest(String interest);
}
