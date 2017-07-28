package flatfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flatfinder.domain.Report;
import flatfinder.domain.User;
import flatfinder.persistence.repository.ReportRepository;
import flatfinder.persistence.repository.UserRepository;

@Controller
public class ReportController {

	@Autowired
	private ReportRepository reportRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping(value="/submitPropertyReport", method=RequestMethod.POST)
	public String catchReport(HttpServletRequest request,
			@RequestParam(value="subject") String subject,
			@RequestParam(value="description") String description,
			@RequestParam(value="propId") Integer propId){
		
		Report report = new Report();
		report.setReportsub(subject);
		report.setDescription(description);
		report.setReporter(SecurityContextHolder.getContext().getAuthentication().getName());
		report.setReportType("Property Report");
		report.setDate("today");
		reportRepo.save(report);
		
		return "redirect:/property?id="+propId+"&interested=false";
		
	}
	
	 @RequestMapping(value="/submitUserReport", method=RequestMethod.POST)
	  public String userReport(HttpServletRequest request,
	      @RequestParam(value="subject") String subject,
	      @RequestParam(value="description") String description,
	      @RequestParam(value="username") String username){
	    
	    User user = userRepo.findByLogin(username);
	    Report report = new Report();
	    report.setReportsub(subject);
	    report.setDescription(description);
	    report.setReporter(SecurityContextHolder.getContext().getAuthentication().getName());
	    report.setReportType("User Report");
	    report.setReportedUsername(user.getLogin());
	    report.setDate("today");
	    reportRepo.save(report);
	    
	    return "redirect:/view-profile";
	    
	  }
	
	@RequestMapping(value="admin-dashboard/reports", method = RequestMethod.GET)
	public ModelAndView showAllReports(){
		ModelAndView modelAndView = new ModelAndView("adminReports");
		List<Report> allReports = (List<Report>) reportRepo.findAll();
		modelAndView.addObject("reports", allReports);
		return modelAndView;
	}

 /**
   * method return string for junit controller test.
   * @return string "report"
   */
  public String getTestMessage(){
    return "report";
  }
	
}
