package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
  public Message findById(Integer id);
  public List<Message> findByTo(String to);
  public List<Message> findByConversationId(Integer conversationId);
}

