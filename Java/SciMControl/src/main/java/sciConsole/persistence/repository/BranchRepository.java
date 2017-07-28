package sciConsole.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.Branch;

public interface BranchRepository extends CrudRepository<Branch, Integer> {
    public Branch findById(int id);
}
