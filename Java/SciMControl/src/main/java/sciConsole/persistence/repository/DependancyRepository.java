package sciConsole.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.Dependancy;

public interface DependancyRepository extends CrudRepository<Dependancy, Integer> {
    public Dependancy findById(int id);
}
