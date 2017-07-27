package cw2.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class Dbconfig {
  @Bean
  public DriverManagerDataSource dataSource(){
    DriverManagerDataSource ds = new DriverManagerDataSource();
    
   ds.setDriverClassName("com.mysql.jdbc.Driver");
   
   /**
    * LOCAL DB CONFIG
    */
   ds.setUrl("jdbc:mysql://localhost:3306/cw2");
   ds.setUsername("cw2");
   ds.setPassword("!CW!zx642120");
   
   /**
    * REMOTE DB CONFIQ
    */
   //ds.setUrl("jdbc:mysql://mysql.mcscw3.le.ac.uk:3306/wrp3");
   //ds.setUsername("wrp3");
   //ds.setPassword("ooxebeej");
   return ds;
  }
}
