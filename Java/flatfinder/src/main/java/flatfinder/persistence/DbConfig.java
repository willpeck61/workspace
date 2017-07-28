package flatfinder.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfig {
  
  /**
   * Setup database configuration.
   **/
  @Bean
  public DriverManagerDataSource dataSource(){
    DriverManagerDataSource ds = new DriverManagerDataSource();
    
    ds.setDriverClassName("com.mysql.jdbc.Driver");
    // jdbc:mysql://host:port/db
    
	 ds.setUrl("jdbc:mysql://localhost:3306/flatfinder");
	 ds.setUsername("root");
	 ds.setPassword("");
    
//   ds.setUrl("jdbc:mysql://mysql.mcscw3.le.ac.uk:3306/cp339");
//   ds.setUsername("cp339");
//   ds.setPassword("actionic");
    
//   ds.setUrl("jdbc:mysql://mysql.mcscw3.le.ac.uk:3306/mic7");
//   ds.setUsername("mic7");
//   ds.setPassword("shigiiho");

    return ds;
  }
}
