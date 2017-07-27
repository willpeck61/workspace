package cw2.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cw2.domain.Question;

public interface QuestionRepository  extends CrudRepository<Question, Integer> {
  public Question findById(Integer id);
  public Question findByExpires(Date date);
  public List<Question> findByExpiresGreaterThanEqual(Date date);
}
