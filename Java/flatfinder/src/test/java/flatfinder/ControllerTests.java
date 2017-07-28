package flatfinder;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import flatfinder.controller.AccountController;
import flatfinder.controller.SearchController;
import flatfinder.controller.FileUploadController;
import flatfinder.controller.MessageController;
import flatfinder.controller.PropertyController;
import flatfinder.controller.DashboardController;
import flatfinder.controller.NotificationController;
import flatfinder.controller.SessionController;

import static org.junit.Assert.assertEquals;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 
import flatfinder.controller.*;

/**
 * Controller Tests 
 * @author willp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FlatFinder.class)
@WebAppConfiguration
public class ControllerTests {
 
  @Test    
  public void testAccountController() throws Exception {
    AccountController controller = new AccountController();
    String view = controller.getTestMessage();
    assertEquals("account", view);    
  }
  
  @Test
  public void testSearchController() throws Exception {
    SearchController controller = new SearchController();
    String view = controller.getTestMessage();
    assertEquals("search", view);
  }
  
  @Test
  public void testSessionController() throws Exception {
    SessionController controller = new SessionController();
    String view = controller.getTestMessage();
    assertEquals("session", view);
  }
  
  @Test
  public void testFileUploadController() throws Exception {
    FileUploadController controller = new FileUploadController();
    String view = controller.getTestMessage();
    assertEquals("upload", view);
  }
  
  @Test
  public void testPropertyController() throws Exception {
    PropertyController controller = new PropertyController();
    String view = controller.getTestMessage();
    assertEquals("property", view);
  }
  
  @Test
  public void testNotificationController() throws Exception {
    NotificationController controller = new NotificationController();
    String view = controller.getTestMessage();
    assertEquals("notification", view);
  }
  
  @Test
  public void testMessageController() throws Exception {
    MessageController controller = new MessageController();
    String view = controller.getTestMessage();
    assertEquals("message", view);
  }
  
  @Test
  public void testDashboardController() throws Exception {
    DashboardController controller = new DashboardController();
    String view = controller.getTestMessage();
    assertEquals("dashboard", view);
  }
}
