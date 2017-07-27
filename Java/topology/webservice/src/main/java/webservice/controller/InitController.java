package webservice.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webservice.domain.Classnode;
import webservice.domain.Datatype;
import webservice.domain.Instance;
import webservice.domain.InstanceProperty;
import webservice.domain.Property;
import webservice.persistence.repository.ClassNodeRepository;
import webservice.persistence.repository.DatatypeRepository;
import webservice.persistence.repository.InstancePropertyRepository;
import webservice.persistence.repository.InstanceRepository;
import webservice.persistence.repository.PropertyRepository;

@Controller
public class InitController {

  @Autowired
  private ClassNodeRepository classnodeRepo;

  @Autowired
  private DatatypeRepository datatypeRepo;

  @Autowired
  private PropertyRepository propertyRepo;

  @Autowired
  private InstanceRepository instanceRepo;
  
  @Autowired
  private InstancePropertyRepository instpropRepo;
  
  @RequestMapping(value = "/rest/class/hierarchy", method = RequestMethod.GET)
  public @ResponseBody String getClassHierarchy(
      @RequestParam(value = "classname", required = true, defaultValue = "all") String classname) {
    Classnode node = new Classnode();
    List<Classnode> subtree = new ArrayList<>();
    node = classnodeRepo.findByName(classname);
    subtree = getSubtree(node, node, null, null);
    String result = toJson(subtree);
    return result;
  }
  
  @RequestMapping(value = "/rest/class/getInstances", method = RequestMethod.GET)
  public @ResponseBody String getInstances() {
    JSONObject json = new JSONObject();
    Collection<Instance> instances = (Collection<Instance>) instanceRepo.findAll();
    if (instances.size() > 0) {
      for (Instance i : instances) {
    	JSONObject jsoninst = new JSONObject();
    	jsoninst.put(i.getClassnodes().getName(), i.getName());
        json.append("instance", jsoninst);
      }
    }
    return json.toString();
  }

  @RequestMapping(value = "/rest/class/nodeData", method = RequestMethod.GET)
  public @ResponseBody String nodeData(@RequestParam(value = "classname", required = true) String classname,
		  @RequestParam(value = "instancename", required = false) String instancename) {
	System.out.println(instancename);  
    Classnode node = classnodeRepo.findByName(classname);
    Instance instance = instanceRepo.findByName(instancename);
    Collection<InstanceProperty> instprop = null;
    Map<Property, String> proppies = new HashMap<>();
    if (!(null == instance)) {
    	node = instance.getClassnodes();
    	assertNotNull(node);
    	instprop = instance.getProperties();
    	for (InstanceProperty ip : instprop) {
    		proppies.put(ip.getProperty(), ip.getValue());
    	}
    } else {
    	System.out.println("instance is null");
    }
    if (null == node) {
      return "Failed - Not found";
    } else {
      JSONObject json = new JSONObject();
      List<Classnode> parentnodes = getParentNodes(node, node, null, null);
      json.put("classname", classname);
      List<Property> properties = new ArrayList<>();
      for (Classnode p : parentnodes) {
        if (!(p.getName().equals(classname))) {
          json.append("superclass", p.getName());
        }
        List<Property> parprops = (List<Property>) p.getProperties();
        if (parprops.size() > 0) {
          for (Property pr : parprops) {
            properties.add(pr);
          }
        }
      }
      if (properties.size() > 0) {
        JSONObject props = new JSONObject();
        for (Property p : properties) {
          props.append("propertyname", p.getName());
          props.append("datatype", p.getDatatype().getName());
          if (!(null == proppies)) {
	          for (Property key : proppies.keySet()) {
	        	  if (key.getName().equals(p.getName())) {
	        		  props.append("value","['" + key.getName() + "','"  + proppies.get(key) + "']");
	        	  }
	          }
          }
        }
        json.put("properties", props);
      }
      return json.toString();
    }
  }

  private List<Classnode> getParentNodes(Classnode node, Classnode root, List<Classnode> subtree,
      List<Classnode> parents) {
    if (null == subtree) {
      subtree = new ArrayList<>();
      parents = new ArrayList<>();
    }
    if (!(node.getRoot())) {
      subtree.add(node);
      parents = (List<Classnode>) node.getParents();
      for (Classnode p : parents) {
        getParentNodes(p, root, subtree, parents);
      }
    }
    return subtree;
  }

  private String toJson(List<Classnode> classlist) {
    Integer curly = 0;
    Integer square = 0;
    Integer level = 0;
    Integer childcount = 0;
    Integer parentid = 0;
    String jsonstr = "{";
    curly++;
    Integer loc = 0;
    for (int i = 0; i < classlist.size(); i++) {
      Classnode current = classlist.get(i);
      if (childcount == 0) {
        if (parentid > 0) {
          if (current.getChildren().size() > 0) {
            curly++;
            jsonstr += ",{\"name\":\"" + current.getName() + "\"";
          } else {
            curly++;
            jsonstr += ",{\"name\":\"" + current.getName() + "\"}";
            curly--;
          }
        } else {
          if (i > 0) {
            for (int n = 0; n < classlist.size(); n++) {
              Classnode parent = classlist.get(n);
              for (Classnode c : current.getParents()) {
                if (parent.getName().equals(c.getName())) {
                  System.out.println(c.getName());
                  loc = n;
                }
              }
            }
            jsonstr += "}";
            curly--;
          }
          if (i > 0) {
            if (loc < (level - 1)) {
              for (int n = 0; n < (level - loc); n++) {
                if (n < (level - loc - 1)) {
                  jsonstr += "]}";
                  square--;
                  curly--;
                } else {
                  if (loc - 1 == level) {
                    jsonstr += "}]";
                    square--;
                    curly--;
                  }
                }
              }
            }
            jsonstr += ",{\"name\":\"" + current.getName() + "\"";
            curly++;
          } else {
            jsonstr += "\"name\":\"" + current.getName() + "\"";
          }
        }
        childcount = current.getChildren().size();
      } else {
        jsonstr += ",\"subclass\":[";
        square++;
        level++;
        for (int n = 0; n < childcount; n++) {
          current = classlist.get(i + n);
          if (n == 0) {
            if (current.getChildren().size() == 0) {
              curly++;
              jsonstr += "{\"name\":\"" + current.getName() + "\"}";
              curly--;
            } else {
              curly++;
              jsonstr += "{\"name\":\"" + current.getName() + "\"";
              break;
            }
          } else {
            if (current.getChildren().size() == 0) {
              curly++;
              jsonstr += ",{\"name\":\"" + current.getName() + "\"}";
              curly--;
            } else {
              curly++;
              jsonstr += ",{\"name\":\"" + current.getName() + "\"";
            }
          }
          i += n;
        }
        if (current.getChildren().size() > 0) {
          childcount = current.getChildren().size();
        } else {
          childcount = 0;
          level--;
          jsonstr += "]";
          square--;
        }
      }
      if (i + 1 == classlist.size()) {
        if (level > 0) {
          jsonstr += "}]";
          curly--;
          square--;
        }
      }
    }
    // }]
    curly--;
    for (int i = 0; i < curly; i++) {
      jsonstr += "}]";
    }
    jsonstr += "}";
    return jsonstr;
  }
  
  private void setInstance(Property prop, Classnode node, String val, String name){
    Instance instance = new Instance();
    instance.setName(name);
    instance.setClassnodes(node);
    instanceRepo.save(instance);
    instance = instanceRepo.findByName("name");
    InstanceProperty instprop = new InstanceProperty();
    instprop.setInstance(instance);
    instprop.setProperty(prop);
    instprop.setValue(val);
    instpropRepo.save(instprop);
  }

  @RequestMapping(value = "/rest/class/createInstance", method = RequestMethod.GET)
  public @ResponseBody String createInstance(
      @RequestParam(value = "formdata", required = true) String formdata) {
    System.out.println(formdata);
    JSONParser parser = new JSONParser();
    try {
      String instancename = null;
      Classnode node = null;
      Map<String, String> properties = new HashMap<>();
      JSONArray json = (JSONArray) parser.parse(formdata);
      Iterator<org.json.simple.JSONObject> i = json.iterator();
      while (i.hasNext()) {
        org.json.simple.JSONObject obj = i.next();
        if (obj.get("name").equals("classname")) {
          node = classnodeRepo.findByName(obj.get("value").toString());
        } else if (obj.get("name").equals("instancename")){
          instancename = obj.get("value").toString();
        } else {
          properties.put(obj.get("name").toString(), obj.get("value").toString());
        }
        System.out.println(obj.get("name"));
        System.out.println(obj.toString());
      }
      for(String k : properties.keySet()){
        Property prop = propertyRepo.findByName(k);
        String val = properties.get(k);
        Datatype type = prop.getDatatype();
        if (type.getName().equals("String")) {
          setInstance(prop, node, val, instancename);
        } else if (type.getName().equals("int")) {
          try {
            Integer cval = Integer.parseInt(val);
            setInstance(prop, node, val, instancename);
          } catch (Exception e){
            return "Failed - Incorrect value for 'int' datatype";
          }
          
        } else if (type.getName().equals("double")) {
          try {
            Double cval = Double.parseDouble(val);
            setInstance(prop, node, val, instancename);
          } catch (Exception e) {
            return "Failed - Incorrect value for 'double' datatype";
          }
        } else if (type.getName().equals("boolean")) {
          try {
            Boolean cval = Boolean.parseBoolean(val);
            setInstance(prop, node, val, instancename);
          } catch (Exception e) {
            return "Failed - Incorrect value for 'boolean' datatype";
          }
        }
      }
      
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "Success - Instance Created";
  }
  
  @RequestMapping(value = "/rest/class/getProperties", method = RequestMethod.GET)
  public @ResponseBody String getProperties() {
    List<Classnode> classes = (List<Classnode>) classnodeRepo.findAll();
    JSONObject json = new JSONObject();
    List<Property> properties = (List<Property>) propertyRepo.findAll();
    if (!(null == properties)) {
      for (Property p : properties) {
        json.append(p.getName(), p.getDatatype().getName());
      }
    }
    if (null == classes && null == properties) {
      return "No data";
    } else {
      return json.toString();
    }
  }
  
  @RequestMapping(value = "/rest/class/getDatatypes", method = RequestMethod.GET)
  public @ResponseBody String getDatatypes() {
    List<Classnode> classes = (List<Classnode>) classnodeRepo.findAll();
    JSONObject json = new JSONObject();
    if (!(null == classes)) {
      for (Classnode c : classes) {
        if (null == datatypeRepo.findByName(c.getName())) {
          Datatype datatype = new Datatype();
          datatype.setName(c.getName());
          datatype.setIsClass(true);
          datatypeRepo.save(datatype);
        }
      }
    }
    List<Datatype> datatypes = (List<Datatype>) datatypeRepo.findAll();
    if (!(null == datatypes)) {
      for (Datatype d : datatypes) {
        json.append(d.getName(), d.getName());
      }
    }
    if (null == classes && null == datatypes) {
      return "No data";
    } else {
      return json.toString();
    }
  }

  @RequestMapping(value = "/rest/class/createProperty", method = RequestMethod.GET)
  public @ResponseBody String createProperty(@RequestParam(value = "classname", required = false) String classname,
      @RequestParam(value = "propertyname", required = true) String propertyname,
      @RequestParam(value = "datatype", required = false) String datatype) {
    Datatype type = datatypeRepo.findByName(datatype);
    Property property = propertyRepo.findByName(propertyname);
    if (!(null == type) && null == property) {

        property = new Property();
        property.setName(propertyname);
        property.setDatatype(type);
       
      Classnode classnode = null;
      if (!(null == classname)) {
        classnode = classnodeRepo.findByName(classname);
        List<Classnode> classlist = new ArrayList<>();
        classlist.add(classnode);
        property.setClassnode(classlist);
      }
      propertyRepo.save(property);
      List<Property> propertylist = new ArrayList<>();
      property = propertyRepo.findByName(propertyname);
      propertylist.add(property);
      if (!(null == classnode)) {
        Collection<Property> classprop = classnode.getProperties();
        classprop.addAll(propertylist);
        classnode.setProperties(classprop);
        classnodeRepo.save(classnode);
      }
      return "Success - Property Created";
    } else {
      Classnode classnode = classnodeRepo.findByName(classname);
      property = propertyRepo.findByName(propertyname);
      
      if (null == property) {
        return "Failed - Property Doesn't Exist or No Type Selected.";
      } else if (property.getClassnode().contains(classnode)) {
    	return "Failed - Property already exists for the class selected";
      } else if (!property.getDatatype().equals(datatype)) {
    	return "Failed - Property name is already in use.";
      } else {
        List<Property> classprop = (List<Property>) classnode.getProperties();
        classprop.add(property);
        classnode.setProperties(classprop);
        classnodeRepo.save(classnode);
        List<Classnode> propclass = (List<Classnode>) property.getClassnode();
        propclass.add(classnode);
        property.setClassnode(propclass);
        propertyRepo.save(property);
        return "Success - Property Assigned to Class.";
      }
    }
  }

  @RequestMapping(value = "/rest/class/deleteProperty", method = RequestMethod.GET)
  public @ResponseBody String deleteProperty(@RequestParam(value = "classname", required = false) String classname,
      @RequestParam(value = "propertyname", required = true) String propertyname) {
    Classnode classnode = null;
    if (!(null == classname)) {
      classnode = classnodeRepo.findByName(classname);
    }
    Property property = propertyRepo.findByName(propertyname);
    List<Instance> instances = new ArrayList<>();
    if (!(null == classnode)) {
      instances = (List<Instance>) classnode.getInstances();
    }
    List<Classnode> classlist = (List<Classnode>) property.getClassnode();
    if (!(null == property) && classlist.size() > 0 && instances.size() == 0) {
      for (Classnode c : classlist) {
        List<Property> propclass = (List<Property>) c.getProperties();
        propclass.remove(property);
        classnodeRepo.save(c);
      }
      property.setClassnode(null);
      propertyRepo.save(property);
      propertyRepo.delete(property);
      return "Success - Deleted";
    } else if (!(null == property) && instances.size() == 0) {
      Collection<Property> classprops = classnode.getProperties();
      classprops.remove(property);
      classnode.setProperties(classprops);
      classnodeRepo.save(classnode);
      Collection<Classnode> propclass = property.getClassnode();
      propclass.remove(classnode);
      property.setClassnode(propclass);
      propertyRepo.save(property);
      propertyRepo.delete(property);
      return "Success - Deleted";
    } else if (instances.size() > 0) {
      return "Failed - You must first stop all instances that use this property.";
    } else {
      return "Failed - Not Found.";
    }
  }

  @RequestMapping(value = "/rest/class/getSubclassList", method = RequestMethod.GET)
  public @ResponseBody String getSubclassList(
      @RequestParam(value = "classname", required = true, defaultValue = "all") String classname,
      @RequestParam(value = "direct", required = false, defaultValue = "false") Boolean direct) {
    Classnode node = new Classnode();
    List<Classnode> subtree = new ArrayList<>();
    if (direct) {
      node = classnodeRepo.findByName(classname);
      subtree = (List<Classnode>) node.getChildren();
    } else if (!(classname.equals("all"))) {
      node = classnodeRepo.findByName(classname);
      subtree = getSubtree(node, node, null, null);
    } else {
      subtree = (List<Classnode>) classnodeRepo.findAll();
    }
    String str = "[";
    int i = 0;
    for (Classnode n : subtree) {
      if (i == 0) {
        str += "\"" + n.getName() + "\"";
        i++;
      } else {
        str += ",\"" + n.getName() + "\"";
      }
    }
    str += "]";
    return str;
  }

  @RequestMapping(value = "/rest/class/createHierarchy", method = RequestMethod.GET)
  public @ResponseBody String createHierarchy(@RequestParam(value = "construct", required = true) String construct,
      @RequestParam(value = "root", required = true) String root) {
    construct.replaceAll("\\s", "");
    Map<String, List<String>> parsed = parseString(null, construct, 0, 0, "", null, null);
    Iterator<Map.Entry<String, List<String>>> iterator = parsed.entrySet().iterator();
    System.out.println(parsed);
    while (iterator.hasNext()) {
      Map.Entry<String, List<String>> subtree = iterator.next();
      List<String> childname = subtree.getValue();
      for (String c : childname) {
        System.out.println("Adding " + c + " to " + subtree.getKey());
        classMaker(c, subtree.getKey(), "");
      }
    }
    iterator = parsed.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, List<String>> subtree = iterator.next();
      Classnode chknoparent = classnodeRepo.findByName(subtree.getKey());
      if (null == chknoparent.getParents()) {
        System.out.println("Adding " + subtree.getKey() + " to root");
        classMaker(subtree.getKey(), root, "");
      }
    }
    return "";
  }

  private Map<String, List<String>> parseString(Map<String, List<String>> nodes, String str, Integer index, Integer bal,
      String name, String parent, List<String> children) {
    if (index < str.length()) {
      if (null == nodes) {
        nodes = new HashMap<>();
        children = new ArrayList<>();
      }
      if (str.charAt(index) == '{') {
        index++;
        if (!(name.equals(""))) {
          if (bal == 1) {
            children = new ArrayList<>();
            nodes.put(name, children);
            parent = name;
          }
          name = "";
        }
        bal++;
        parseString(nodes, str, index, bal, name, parent, children);
      } else if (str.charAt(index) == '}') {
        index++;
        if (!(name.equals(""))) {
          if (bal == 1) {
            children = new ArrayList<>();
            nodes.put(name, children);
            parent = name;
          } else {
            children = nodes.get(parent);
            children.add(name);
            nodes.put(parent, children);
          }
        }
        bal--;
        name = "";
        parseString(nodes, str, index, bal, name, parent, children);
      } else if (str.charAt(index) == ',') {
        index++;
        if (!(name.equals(""))) {
          if (bal == 1) {
            nodes.put(name, children);
            parent = name;
          } else if (bal > 1) {
            children = nodes.get(parent);
            children.add(name);
            nodes.replace(parent, children);
          }
          name = "";
        }
        parseString(nodes, str, index, bal, name, parent, children);
      } else {
        name += str.charAt(index);
        if (str.charAt(index + 1) == '{' && bal > 1) {
          children = nodes.get(parent);
          children.add(name);
          nodes.replace(parent, children);
          bal = 1;
        }
        index++;
        parseString(nodes, str, index, bal, name, parent, children);
      }
    }
    return nodes;
  }

  @RequestMapping(value = "/rest/class/delete", method = RequestMethod.GET)
  public @ResponseBody Boolean deleteClass(@RequestParam(value = "classname", required = true) String classname) {
    Classnode parentnode = classnodeRepo.findByName(classname);
    if (null == parentnode) {
      return false;
    } else {
      List<Classnode> children = (List<Classnode>) parentnode.getChildren();
      Classnode child = null;
      Classnode parent = null;
      for (Classnode c : children) {
        List<Classnode> childparents = (List<Classnode>) c.getParents();
        if (!(null == childparents) && childparents.size() > 1) {
          for (Classnode p : childparents) {
            if (p.getName().equals(parentnode.getName())) {
              parent = p;
              child = c;
            }
          }
        }
        if (!(null == parent)) {
          childparents.remove(parent);
          c.setParents(childparents);
          classnodeRepo.save(c);
        }
      }
      children.remove(child);
      parentnode.setChildren(children);
      Collection<Instance> instances = parentnode.getInstances();
      if (instances.size() > 0) {
        parentnode.setInstances(null);
        classnodeRepo.save(parentnode);
        instanceRepo.delete(instances);
      }

      Collection<Property> classprop = parentnode.getProperties();
      for (Property p : classprop) {
    	  Collection<Classnode> nodes = p.getClassnode();
    	  nodes.remove(parentnode);
    	  p.setClassnode(nodes);
    	  propertyRepo.save(p);
      }
      parentnode.setProperties(null);
      
      classnodeRepo.save(parentnode);
      classnodeRepo.delete(parentnode);
      return true;
    }
  }

  private List<Classnode> getSubtree(Classnode node, Classnode root, List<Classnode> subtree,
      List<Classnode> siblings) {
    if (null == subtree) {
      subtree = new ArrayList<>();
      siblings = new ArrayList<>();
    }
    subtree.add(node);
    siblings = (List<Classnode>) node.getChildren();
    for (Classnode c : siblings) {
      getSubtree(c, root, subtree, siblings);
    }
    return subtree;
  }

  private String classMaker(String child, String parent, String edit) {
    Classnode childnode = classnodeRepo.findByName(child);
    if (!(null == edit) && edit.length() > 0){
      childnode = classnodeRepo.findByName(edit);
      if (childnode.getInstances().size() > 0) {
    	  return "Failed - Property in use.";
      } else {
    	  childnode.setName(child);
    	  classnodeRepo.save(childnode);
      }
    } 	
    if (null == childnode) {
      childnode = new Classnode();
      childnode.setRoot(false);
      childnode.setName(child);
      classnodeRepo.save(childnode);
      childnode = classnodeRepo.findByName(child);
    }
    if (parent.equals("ROOT")) {
      childnode.setName(child);
      childnode.setRoot(true);
      classnodeRepo.save(childnode);
      childnode = classnodeRepo.findByName(child);
      return "Root node added";
    } else {
      Classnode parentnode = classnodeRepo.findByName(parent);
      if (null == childnode.getRoot() || childnode.getRoot()) {
        childnode.setRoot(false);
        classnodeRepo.save(childnode);
        childnode = classnodeRepo.findByName(child);
      }
      if (null == parentnode) {
        parentnode = new Classnode();
        parentnode.setName(parent);
        classnodeRepo.save(parentnode);
        parentnode = classnodeRepo.findByName(parent);
      }
      List<Classnode> parents = (List<Classnode>) childnode.getParents();
      if (null == parents) {
        parents = new ArrayList<>();
      }
      parents.add(parentnode);
      childnode.setParents(parents);
      classnodeRepo.save(childnode);
      List<Classnode> children = (List<Classnode>) parentnode.getChildren();
      if (null == children) {
        children = new ArrayList<>();
      }
      children.add(childnode);
      parentnode.setChildren(children);
      classnodeRepo.save(parentnode);
    }
    return "";
  }

  @RequestMapping(value = "/rest/class/create", method = RequestMethod.GET)
  public @ResponseBody String createClass(@RequestParam(value = "classname", required = true) String child,
      @RequestParam(value = "superclass", required = false, defaultValue = "ROOT") String parent,
      @RequestParam(value = "edit", required = false) String edit) {
	  Classnode chkclass = classnodeRepo.findByName(child);
	  Classnode parentclass = classnodeRepo.findByName(parent);
	  if (!(null == chkclass) && !(null == parentclass)) { 
		  if (chkclass.getParents().contains(parentclass)) {
			  return "Failed - Relationship already exists";
		  } else {
			  classMaker(child, parent, edit); 
		  }
	  } else {
		  classMaker(child, parent, edit);
	  }
    return child + " added successfully to parent " + parent;
  }
}
