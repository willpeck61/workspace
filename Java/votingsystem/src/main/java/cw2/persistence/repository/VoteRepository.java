package cw2.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cw2.domain.Question;
import cw2.domain.User;
import cw2.domain.Vote;

public interface VoteRepository  extends CrudRepository<Vote, Integer> {
  public Vote findById(Integer id);
  public List<Vote> findByUser(User user);
  public Vote findByUserAndQuestion(User user, Question question);
  public List<Vote> findByQuestion(Question question);
}
