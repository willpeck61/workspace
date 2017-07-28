package flatfinder.domain;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reports")
public class Report {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Integer id;
	  
	  @Column(name = "reporter")
	  private String reporter;
	 
	  @Column(name = "description")
	  private String description;
	  
	  @Column(name = "date")
	  private String date;
	  
	  @Column(name = "reportType")
	  private String reportType;
	  
	  @Column(name = "reportsub")
	  private String reportsub;
	  
	  private String reportedUsername;
	  
	  public void setId(Integer Id){
		  this.id = Id;
	  }
	  
	  public Integer getId(){
		  return id;
	  }
	  
	  public void setReporter(String r){
		  this.reporter = r;
	  }
	  
	  public String getReporter(){
		  return reporter;
	  }
	  
	  public void setDescription(String d){
		  this.description = d;
	  }
	  
	  public String getDescription(){
		  return description;
	  }
	  
	  public void setDate(String dt){
		  if(dt.equals("today")){
			  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			  Date d = new Date();
			  this.date = dateFormat.format(d);
		  } else {
			  this.date = dt;
		  }
	  }
	  
	  public String getDate(){
		  return date;
	  }
	  
	  public void setReportType(String r){
		  this.reportType = r;
	  }
	  
	  public String getReportType(){
		  return reportType;
	  }

	  public void setReportsub(String r){
		  this.reportsub = r;
	  }
	  
	  public String getReportsub(){
		  return reportsub;
	  }

    public String getReportedUsername() {
      return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
      this.reportedUsername = reportedUsername;
    }

	  

}
