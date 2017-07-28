package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
  public Notification findById(Integer id);
  public List<Notification> findByForUser(String forUser);
  public List<Notification> findByIsSeenAndForUser(Boolean isSeen, String forUser);
  public List<Notification> findByForUserAndCreatedBy(String forUser, String createdBy);
  public Notification findByRequestId(Integer id);
  public Notification findByPropertyPostcode(String postcode);
 }

