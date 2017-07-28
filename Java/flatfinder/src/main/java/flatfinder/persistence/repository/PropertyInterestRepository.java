package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.PropertyInterest;

public interface PropertyInterestRepository extends CrudRepository<PropertyInterest, Integer> {
  public PropertyInterest findById(Integer id);
  public List<PropertyInterest> findByUserIdAndPropertyId(Integer userId, Integer propertyId);
  public List<PropertyInterest> findByUserId(Integer userId);
}

