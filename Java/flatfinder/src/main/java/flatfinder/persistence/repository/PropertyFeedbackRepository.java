package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.PropertyFeedback;

public interface PropertyFeedbackRepository extends CrudRepository<PropertyFeedback, Integer> {
	public PropertyFeedback findById(Integer id);
	public List<PropertyFeedback> findByUserName(String userName);
	public List<PropertyFeedback> findByPropertyId(Integer propertyId);
}

