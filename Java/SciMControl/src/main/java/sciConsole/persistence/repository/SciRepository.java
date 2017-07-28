package sciConsole.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.Sci;
import sciConsole.domain.Ver;

public interface SciRepository extends CrudRepository<Sci, Integer> {
    public Sci findById(int id);
    public Sci findByName(String name);
}