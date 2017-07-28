package sciConsole.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sciConsole.domain.FileRepo;

public interface FileRepoRepository extends CrudRepository<FileRepo, Integer> {
    public FileRepo findById(int id);
}
