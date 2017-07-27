package webservice.persistence;

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
    //ds.setUrl("jdbc:mysql://localhost:3306/wrp3");
    //ds.setUsername("wrp3");
    //ds.setPassword("ooxebeej");
 
    ds.setUrl("jdbc:mysql://mysql.mcscw3.le.ac.uk:3306/wrp3");
    ds.setUsername("wrp3");
    ds.setPassword("ooxebeej");
    
    return ds;
  }
}
