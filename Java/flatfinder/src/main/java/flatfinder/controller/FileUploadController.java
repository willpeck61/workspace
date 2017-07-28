package flatfinder.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import flatfinder.FlatFinder;
import flatfinder.persistence.repository.PropertyRepository;

@Controller
public class FileUploadController {

  @RequestMapping(method = RequestMethod.GET, value = "/upload")
  public String provideUploadInfo(Model model) {
    File rootFolder = new File(FlatFinder.ROOT);
    List<String> fileNames = Arrays.stream(rootFolder.listFiles())
      .map(f -> f.getName())
      .collect(Collectors.toList());
    System.out.println("File uploading..");
    model.addAttribute("files",
      Arrays.stream(rootFolder.listFiles())
          .sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
          .map(f -> f.getName())
          .collect(Collectors.toList())
    );

    return "view-profile";
  }
  
  /**
   * Generic file upload controller.  Requires form to provide path
   * name and type eg. profile for profile page. 
   * @param name  path and name.  This could be improved.
   * @param file  the file encapulated as multipart
   * @param type  profile, property or room 
   * @param redirectAttributes  validation messages.
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "/upload")
  public String handleFileUpload(@RequestParam("name") String name,
                   @RequestParam("file") MultipartFile file,
                   @RequestParam("type") String type,
                   RedirectAttributes redirectAttributes) {

    if (!file.isEmpty()) {
      try {
        
        BufferedOutputStream stream = new BufferedOutputStream(
            new FileOutputStream(new File(FlatFinder.ROOT + "/" + name)));
        FileCopyUtils.copy(file.getInputStream(), stream);
        stream.close();
        redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + name + "!");
        
      }
      catch (Exception e) {
        redirectAttributes.addFlashAttribute("message",
            "You failed to upload " + name + " => " + e.getMessage());
      }
    }
    else {
      redirectAttributes.addFlashAttribute("message",
          "You failed to upload " + name + " because the file was empty");
    }
    String redir = null;
    System.out.println("File uploaded..");
    //Redirect based on hidden field sent from view. Add other locations as needed.
    if (type.equals("profile")){
      redir = "view-profile";
    }
    
    return "redirect:" + redir;
  }
  
  public String getTestMessage(){
    return "upload";
  }
}