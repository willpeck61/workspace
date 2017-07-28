package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Inbox;

public interface InboxRepository extends CrudRepository<Inbox, Integer> {
	public Inbox findById(Integer id);
	public List<Inbox> findBySender(String f);
	public List<Inbox> findByReceiver(String t);
}

