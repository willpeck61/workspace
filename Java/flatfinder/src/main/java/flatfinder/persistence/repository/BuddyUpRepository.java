package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.BuddyUp;

public interface BuddyUpRepository extends CrudRepository<BuddyUp, Integer> {
  public BuddyUp findById(Integer id);
  public BuddyUp findByInitialiserIdAndResponderId(Integer intid, Integer respid);
  public List<BuddyUp> findByInitialiserId(Integer intid);
  public List<BuddyUp> findByInitialiserIdOrResponderIdAndConfirmed(Integer intid, Integer respid, Integer confirmed);
  public List<BuddyUp> findByInitialiserIdOrResponderId(Integer intid, Integer respid);
}

