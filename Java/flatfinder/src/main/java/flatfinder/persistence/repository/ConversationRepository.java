package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Conversation;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {
  public Conversation findById(Integer id);
  public List<Conversation> findBySender(String sender);
  public List<Conversation> findByRecipient(String recipient);
}

