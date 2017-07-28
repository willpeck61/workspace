package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Integer> {
	public ConfirmationToken findById(Integer id);
	public List<ConfirmationToken> findByforUser(String user);
	public List<ConfirmationToken> findByTokenType(String type);
}


