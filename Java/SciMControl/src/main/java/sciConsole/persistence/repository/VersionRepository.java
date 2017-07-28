package sciConsole.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.Ver;

public interface VersionRepository extends CrudRepository<Ver, Integer> {
    public Ver findById(int id);
    public Ver findByName(String name);
    public Ver findBySciIdAndName(Integer sciid, String name);
}