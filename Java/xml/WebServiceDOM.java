package uk.ac.le.cs.wt;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

/**
 * Assignment One - CO3098.
 * @author wrp3
 */

public class WebServiceDOM {
  
  /**
   * Main method for XML parser.
   * @param args - unused command line args
   */
  public static void main(String[] args) {
    try {
      DOMParser parser = new DOMParser();
      parser.parse("WebService.xml");
      Document doc = parser.getDocument();
      traverse_tree(doc);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }
  
  /**
   * While traversing find elements by
   * node name and store generated strings
   * in hash-map.  Once complete, output
   * hash-map stored strings into desired
   * format.
   * @param node - current node
   */
  public static void traverse_tree(Node node) {
    int type = node.getNodeType();
    if (type == Node.ELEMENT_NODE) {
      if (node.hasChildNodes()) {
        int cnodes = node.getChildNodes().getLength();
        NodeList nlist = node.getChildNodes();
        for (int i = 0; i < cnodes; i++ ) {
          HashMap<Integer, String> out = new HashMap<>();
          String nname = (nlist.item(i).getNodeName());
          if (nname.equals("abstract_method")) {
            out = foundMethod(nlist.item(i), out);
            traverse_tree(nlist.item(i));
          }
          outputStrings(out);
        }
      }
    } else if (type == Node.DOCUMENT_NODE) {
      traverse_tree(((Document) node).getDocumentElement());
    }
  }
  
  /**
   * Output strings stored in hash-map
   * into desired format.
   * @param out - hash-map with stored output strings. 
   */
  private static void outputStrings(HashMap<Integer, String> out) {
    if (!(null == out.get(0))) {
      System.out.print(
          out.get(0) + " " 
              + out.get(1) + " " 
              + out.get(2) + "(" 
              + out.get(3) + ")"
      );
      if (!(null == out.get(4))) {
        System.out.print(" throws " + out.get(4) + ";\n");
      } else {
        System.out.println(";\n");
      }
    }
  }
  
  /**
   * Once abstract_method element is found,
   * iterate through child elements and
   * store output strings in hash-map.
   * @param node - abstract_method node
   * @param out - hash-map for storing output strings.
   * @return fully populated hash-map
   */
  private static HashMap<Integer, String> foundMethod(
      Node node, HashMap<Integer, String> out) {
    String methname = ((Element) node).getAttribute("name");
    NodeList elems = node.getChildNodes();
    out.put(2, methname);
    int len = elems.getLength();
    for (int x = 0; x < len; x++) {
      if (!(elems.item(x).getNodeName().equals("#text"))) {
        Node cnode = elems.item(x);
        if (cnode.getNodeName().equals("modifier")) {
          out.put(0, cnode.getTextContent());
        } else if (cnode.getNodeName().equals("return")) {
          out.put(1, cnode.getTextContent());
        } else if (cnode.getNodeName().equals("arguments")) {
          out.put(3, nodeCrawler(elems.item(x), out.get(3)));
        } else if (cnode.getNodeName().equals("exceptions")) {
          out.put(4, nodeCrawler(elems.item(x), out.get(4)));  
        }
        cnode.getNodeName();
      }
    }
    return out;
  }
  
  /**
   * Takes current position in the tree
   * with relevant string stored in hash-map
   * and traverses subtree child nodes to produce
   * desired output string for parameters
   * and exceptions. 
   * @param node - current position
   * @param out - current output string stored in hash-map
   * @return - new string to be stored in hash-map
   */
  private static String nodeCrawler(Node node, String out) {
    NodeList nodes = node.getChildNodes();
    for (int k = 0; k < nodes.getLength(); k++) {
      if (!(nodes.item(k).getNodeName().equals("#text"))) {
        Node pnode = nodes.item(k);
        String ps = null;
        if (!(null == out)) {
          ps = out;
          if (pnode.getNodeName().equals("exception")) {
            out = ps + ", " + pnode.getTextContent();
          } else if (pnode.getNodeName().equals("parameter")) {
            out = ps + ", " 
                + ((Element) pnode).getAttribute("type") 
                + " " + pnode.getTextContent();
          }
        } else {
          if (pnode.getNodeName().equals("exception")) {
            out = pnode.getTextContent();
          } else if (pnode.getNodeName().equals("parameter")) {
            out = ((Element) pnode).getAttribute("type") 
                + " " + pnode.getTextContent();
          }
        }
      }
    }
    return out;
  }
}