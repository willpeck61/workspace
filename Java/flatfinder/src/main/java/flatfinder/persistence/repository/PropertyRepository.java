package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Property;

public interface PropertyRepository extends CrudRepository<Property, Integer> {
  public Property findById(Integer id);
  
  public List<Property> findByUserId(Integer userId);
  
  public List<Property> findByPostcode(String postcode, Pageable topTen);
  public List<Property> findByAddress3(String city, Pageable topTen);
  public List<Property> findByAddress1(String address, Pageable topTen);
  
  public List<Property> findByPostcode(String postcode);
  public List<Property> findByAddress3(String city);
  public List<Property> findByAddress1(String address);
  
  public List<Property> findByAddress3AndAddress1(String city, String address);

}

