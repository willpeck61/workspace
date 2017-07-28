package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
  public Profile findById(Integer id);
  public Profile findByUserid(Integer userid);
  
  public List<Profile> findByEnsuite(Integer ensuite);
  public List<Profile> findByQuietorlively(Integer quietorlively);
  public List<Profile> findBySamesexormixed(Integer samesexormixed);
  public List<Profile> findBySmoking(Integer smoking);
  public List<Profile> findByPets(Integer pets);
  public List<Profile> findByStudyyear(Integer studyyear);
  public List<Profile> findByMaxsharers(Integer maxsharers);
  public List<Profile> findByStudyingOrWorkplace(String querystr1, String querystr2);
  public List<Profile> findByHeadline(String querystr1);
  public List<Profile> findByAbout(String querystr1);
  public List<Profile> findByFacebookurl(String querystr1);
  public List<Profile> findByTwitterurl(String querystr1);
  public List<Profile> findByGoogleplusurl(String querystr1);
  public List<Profile> findByStudyplace(String querystr1);
  public List<Profile> findByWpostcode(String querystr1);
  public List<Profile> findBySpostcode(String querystr1);
  public List<Profile> findByStudying(String querystr1);
  public List<Profile> findByWorkplace(String querystr1);
  
}

