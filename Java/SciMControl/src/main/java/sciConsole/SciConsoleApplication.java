package sciConsole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sciConsole.domain.Role;
import sciConsole.domain.Sci;
import sciConsole.domain.User;
import sciConsole.domain.Ver;
import sciConsole.persistence.repository.SciRepository;
import sciConsole.persistence.repository.UserRepository;
import sciConsole.persistence.repository.VersionRepository;
            
@SpringBootApplication
public class SciConsoleApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(SciConsoleApplication.class, args);
    }
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private VersionRepository verRepo;
    
    @Autowired
    private SciRepository sciRepo;
    
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Application Init");
        try {
	    	BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
	    	Role role = new Role();
        		role.setId(ROLE_USER);
        		role.setRole("DEVELOPER");
	        User user = new User();
	        	user.setLogin("Alice");
	        	user.setPassword(pe.encode("password"));
	        	user.setRole(role);
	        userRepo.save(user);
	        role = new Role();
        		role.setId(ROLE_ADMIN);
        		role.setRole("MANAGER");
	        user = new User();
	        	user.setLogin("Bob");
	        	user.setPassword(pe.encode("admin"));
	        	user.setRole(role);
	        userRepo.save(user);
	        Sci sci = new Sci();
	        	sci.setName("SCI_1");
	        	sci.setSciDesc("This is a test SCI entry");
	        sciRepo.save(sci);
	        Ver version = new Ver();
        		version.setName("version_2");
        		version.setSciId(1);
        		version.setVerDesc("This is a test version entry");
        		version.setStatus(1);
        		version.setCreatedBy(2);
        	verRepo.save(version);
        } catch (Exception e) {
        	System.out.println("Default users already active.");
        }
        
    }
}