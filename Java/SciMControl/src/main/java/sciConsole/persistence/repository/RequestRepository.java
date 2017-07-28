package sciConsole.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.Request;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    public Request findById(int id);
    List<Request> findByStatus(Integer status);
    List<Request> findByStatusAndAssignTo(Integer status, Integer assignTo);
}