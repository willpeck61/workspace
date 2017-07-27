package webservice;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import webservice.domain.Classnode;
import webservice.domain.Datatype;
import webservice.persistence.repository.ClassNodeRepository;
import webservice.persistence.repository.DatatypeRepository;

@SpringBootApplication
public class WebserviceApplication implements ApplicationRunner {

  @Autowired
  private ClassNodeRepository classnodeRepo;
  
  @Autowired
  private DatatypeRepository datatypeRepo;
  
	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Datatype type = new Datatype();
    type.setName("int");
    datatypeRepo.save(type);
    
    type = new Datatype();
    type.setName("String");
    type.setIsClass(false);
    datatypeRepo.save(type);
    
    type = new Datatype();
    type.setName("double");
    type.setIsClass(false);
    datatypeRepo.save(type);
    
    type = new Datatype();
    type.setName("boolean");
    type.setIsClass(false);
    datatypeRepo.save(type);
    
   /* for (int i = 0; i < 10; i++) {
      Classnode testclass = new Classnode();
      testclass.setName("Test" + i);
      classnodeRepo.save(testclass);
    }
    List<Classnode> classes = (List<Classnode>) classnodeRepo.findAll();
    for (int i = 0; i < 50; i++) {
      Classnode parentclass = classnodeRepo.findByName("Test" + 
    ThreadLocalRandom.current().nextInt(0, classes.size()));
      Classnode childclass = classnodeRepo.findByName("Test" + 
    ThreadLocalRandom.current().nextInt(0, classes.size()));
      addNodes(parentclass.getName(), childclass.getName());
    }*/
  }
  
  public void addNodes(String parentnode, String childnode) {
    Classnode parent = classnodeRepo.findByName(parentnode);
    Classnode child = classnodeRepo.findByName(childnode);
    if (!(parent.getName().equals(child.getName()))) {
      Set<Classnode> parents = new HashSet<>();
      Set<Classnode> children = new HashSet<>();
      parents.add(parent);
      children.add(child);
      parent.setChildren(children);
      child.setParents(parents);
      classnodeRepo.save(child);
      classnodeRepo.save(parent);
    }
  }
}
