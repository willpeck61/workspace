package sciConsole.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sciConsole.domain.Request;
import sciConsole.domain.Sci;
import sciConsole.domain.User;
import sciConsole.domain.Ver;
import sciConsole.persistence.repository.RequestRepository;
import sciConsole.persistence.repository.SciRepository;
import sciConsole.persistence.repository.UserRepository;
import sciConsole.persistence.repository.VersionRepository;

@Controller
public class ChangeRequestController {
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RequestRepository reqRepo;
	
	@Autowired
	private SciRepository sciRepo;
	
	@Autowired
	private VersionRepository verRepo;

	private Date parseDate;

	private List<Ver> verlist;

	private List<Sci> scilist;

	private Request req;

	private User currentUser;
    
    /*
     * dashboard returns updated attributes for the dashboard view
     * on the "success-login-user" view.
     */
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public ModelAndView dashBoard(HttpServletRequest request, Model model){
    	model.addAttribute("pendingqty", reqRepo.findByStatus(0).size());
    	model.addAttribute("activeqty", reqRepo.findByStatus(1).size());
    	model.addAttribute("completeqty", reqRepo.findByStatus(2).size());
    	return new ModelAndView("dashboard","model",model);
    }
    
    /*
     * newChange receives params to create a new change Request.
     * The method then returns the updated request via the individual model 
     * Request attributes accessible through "new-change-request" view.
     */
    @RequestMapping(value="/newChange", method=RequestMethod.GET)
    public ModelAndView newChange(HttpServletRequest request,
    	@RequestParam(value="sci", required=true) String sciname,
		@RequestParam(value="version", required=true) String versionname,
		@RequestParam(value="description", required=true) String description,
		@RequestParam(value="solution", required=true) String solution,
    	@RequestParam(value="type", required=true) Integer type,
		Model model){
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User currentUser;
    		
    		try {
    			currentUser = userRepo.findByLogin(auth.getName());
    		} catch (Exception e){
    			currentUser = userRepo.findByLogin("Bob");
    		}
    		
    		Sci scinom = sciRepo.findByName(sciname);
    		Ver vernom = verRepo.findBySciIdAndName(scinom.getId(), versionname);
    		Request req = new Request();
    		
    		Date duedate = new Date();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(duedate);
    		cal.add(Calendar.DATE, 14);
    		
    		req.setSciId(scinom.getId());
    		req.setVersionId(vernom.getId());
    		req.setReqDesc(description);
    		req.setReqSolution(solution);
    		req.setCreatedBy(currentUser.getId());
    		req.setDueDate(cal.getTime());
    		req.setType(type);
    		req.setStatus(0);
    		reqRepo.save(req);
	
    		Sci sci = sciRepo.findById(vernom.getSciId());
    		model.addAttribute("sciid",sci.getId());
    		model.addAttribute("versionid",vernom.getId());
    		model.addAttribute("description",description);
    		model.addAttribute("solution",solution);
    		model.addAttribute("type",type);
    		model.addAttribute("createdby",currentUser.getId());
    		model.addAttribute("duedate",cal.getTime());
    		model.addAttribute("sciname",sci.getName());
    		model.addAttribute("vername",vernom.getName());
    		
    		return new ModelAndView("new-change-request","req",req);
    }
    
    /*
     * getPendingRequests is called by "assess-change-request" view. It returns
     * all pending requests (status "0") through "pending" model attribute, all sci artifacts
     * through "scilist" and all versions through "verlist" attributes.  These
     * are accessible through the "return-pending-requests" view.
     */
    @RequestMapping(value="/get-pending-requests", method=RequestMethod.GET)
    public ModelAndView pendingRequests(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView modview = new ModelAndView("return-pending-requests");
    	List<Request> pending = reqRepo.findByStatus(0);
    	verlist = new ArrayList<Ver>();
    	scilist = new ArrayList<Sci>();
    	for(Request r : pending){
    		verlist.add(verRepo.findById(r.getVersionId()));
    		scilist.add(sciRepo.findById(r.getSciId()));
    	}
    	modview.addObject("pending",pending);
    	modview.addObject("scilist",scilist);
    	modview.addObject("verlist",verlist);
    	return modview;
    }
    
    /*
     * updatePending receives the selected Request params from the "assess-change-request"
     * view and updates the Request by "reqid" (Request.id). Then sets the Request status
     * to either "1" for accept or "4" for reject. If status "1" then priority, assigned to user
     * and due date are set. For status "4" reason for rejection is set.    
     */
    @RequestMapping(value="/update-pending-requests", method=RequestMethod.GET)
    public ModelAndView updatePending(HttpServletRequest request, 
    		@RequestParam(value="reqid", required=true) Integer reqid,
    		@RequestParam(value="status", required=true) Integer state,
    		@RequestParam(value="priority", required=false) String priority, 
    		@RequestParam(value="assignto", required=false) Integer usrid,
    		@RequestParam(value="duedate", required=false) String date,
    		@RequestParam(value="reason", required=false) String reason){
    	SimpleDateFormat stringDate = new SimpleDateFormat("dd-mm-yy");
		try {
			parseDate = stringDate.parse(date);
		} catch (Exception e){
			//do nothing
		}
    	Request updreq = reqRepo.findById(reqid);
    	updreq.setStatus(state);
    	if(state == 4){
    		updreq.setRejectReason(reason);
    	}
    	if(usrid != null){
    		updreq.setAssignTo(usrid);
    		updreq.setPriority(priority);
    		updreq.setDueDate(parseDate);
    	}
    	reqRepo.save(updreq);
    	return new ModelAndView("return-pending-requests");
    }
    
    /*
     * returnSciVersion returns all active Sci artifacts and versions accessible
     * via the attributes "scis" and "versions" through the "return-sci-version" view.
     */
    @RequestMapping(value="/return-sci-version", method=RequestMethod.GET)
    public ModelAndView returnSciVersion(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView modview = new ModelAndView("return-sci-version");
    	modview.addObject("scis",sciRepo.findAll());
    	modview.addObject("versions",verRepo.findAll());
    	return modview;
    }
    
    /*
     * assignedRequests returns the ArrayList of assigned Requests for the
     * currentUser.  If user is a MANAGER then all assigned requests are 
     * returned to the "assigned-requests" view through the "assigned"
     * model attribute.
     */
    @RequestMapping(value="/assigned-requests", method=RequestMethod.GET)
    public ModelAndView assignedRequests(HttpServletRequest request, HttpServletResponse response){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	List<Request> assigned;
    	try {
    		currentUser = userRepo.findByLogin(auth.getName());
    	} catch (Exception e){
    		currentUser = userRepo.findByLogin("Alice");
    	}
		if (currentUser.getRole().equals(userRepo.findByLogin("Bob").getRole())){
			assigned = reqRepo.findByStatus(1);
		} else {
			assigned = reqRepo.findByStatusAndAssignTo(1, currentUser.getId());
		}
		
		List<Ver> verlst = new ArrayList<Ver>();
    	List<Sci> scilst = new ArrayList<Sci>();
    	for(Request r : assigned){
    		verlst.add(verRepo.findById(r.getVersionId()));
    		scilst.add(sciRepo.findById(r.getSciId()));
    	}
    	ModelAndView modview = new ModelAndView("assigned-requests");
    	modview.addObject("assigned", assigned);
    	modview.addObject("scilst", scilst);
    	modview.addObject("verlst", verlst);
    	return modview;
    }
    
    
    /*
     * completeRequest updates selected Request to Status of 2 and DateComplete
     * after receiving "reqid"->(Request.id) and "complete"->(Request.dateComplete)
     * params from View "assigned-requests". Then the method returns "assigned" attribute
     * with assigned requests to currentUser unless role is "MANAGER" where instead, all are
     * returned to "assigned-requests" view.
     */
    
    @RequestMapping(value="/complete-request", method=RequestMethod.GET)
    public ModelAndView completeRequest(HttpServletRequest request,
    		@RequestParam(value="reqid", required=true) Integer reqid, 
    		@RequestParam(value="complete", required=true) String complete, HttpServletResponse response){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	SimpleDateFormat stringDate = new SimpleDateFormat("dd-mm-yy");
		try {
			parseDate = stringDate.parse(complete);
		} catch (Exception e){
			//do nothing
		}
    	req = reqRepo.findById(reqid);
    	
    	req.setStatus(2);
    	req.setDateComplete(parseDate);
    	reqRepo.save(req);
    	
    	List<Request> assigned;
    	
    	try {
    		currentUser = userRepo.findByLogin(auth.getName());
    	} catch (Exception e){
    		currentUser = userRepo.findByLogin("Alice");
    	}
    
    	if (currentUser.getRole().equals(userRepo.findByLogin("Bob").getRole())){
			assigned = reqRepo.findByStatus(1);
		} else {
			assigned = reqRepo.findByStatusAndAssignTo(1, currentUser.getId());
		}
    
    	List<Ver> verlst = new ArrayList<Ver>();
    	List<Sci> scilst = new ArrayList<Sci>();
    	for(Request r : assigned){
    		verlst.add(verRepo.findById(r.getVersionId()));
    		scilst.add(sciRepo.findById(r.getSciId()));
    	}
    	ModelAndView modview = new ModelAndView("assigned-requests");
    	modview.addObject("assigned", assigned);
    	modview.addObject("scilst", scilst);
    	modview.addObject("verlst", verlst);
    	return modview;
    }
}

