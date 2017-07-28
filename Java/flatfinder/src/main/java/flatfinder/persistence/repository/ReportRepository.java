package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Report;

public interface ReportRepository extends CrudRepository<Report, Integer> {
	
  public Report findById(Integer id);
  public List<Report> findByReportType(String reportType);
  public List<Report> findByReporterAndReportedUsername(String username, String rid);
  
}
