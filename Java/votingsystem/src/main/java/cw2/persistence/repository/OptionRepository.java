package cw2.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cw2.domain.Option;
import cw2.domain.Question;

public interface OptionRepository extends CrudRepository<Option, Integer> {
  public Option findById(Integer id);
  public List<Option> findByQuestion(Question question);
}
