package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.UserPropertyHistory;

public interface UserPropertyHistoryRepository extends CrudRepository<UserPropertyHistory, Integer> {
  public List<UserPropertyHistory> findByUserId(Integer userId);
  public UserPropertyHistory findByUserIdAndPropertyId(Integer userId, Integer propertyId);
}

