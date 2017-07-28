package sciConsole.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfig {
    @Bean
    public DriverManagerDataSource dataSource() {        
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // jdbc:mysql://host:port/db
        ds.setUrl("jdbc:mysql://mysql.mcscw3.le.ac.uk:3306/wrp3");
        ds.setUsername("wrp3");
        ds.setPassword("ooxebeej");
        return ds;
    }
}