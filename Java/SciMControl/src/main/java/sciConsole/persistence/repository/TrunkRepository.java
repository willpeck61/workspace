package sciConsole.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.Trunk;

public interface TrunkRepository extends CrudRepository<Trunk, Integer> {
    public Trunk findById(int id);
}
