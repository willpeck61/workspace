package cw2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/", "/registration",
              "/process-registration","/logout")
          .anonymous()
    .and()
      .authorizeRequests()
        .antMatchers("/process-login")
          .authenticated()
    .and()
      .authorizeRequests()
        .antMatchers("/voter-page","/place-vote",
            "return-options")
          .hasRole("VOTER")
    .and()
      .authorizeRequests()
        .antMatchers("/officer-page","/add-question",
            "/modify-question", "/check-votes",
            "/display-results", "/add-securecode")
          .hasRole("ERO")
          /*.antMatchers("/process-login","/officer-page",
              "/add-question","/add-securecode",
              "/modify-question","/display-results",
              "/check-votes","return-options",
              "/place-vote", "/voter-page")
          .authenticated()*/
    .and()
      .formLogin()
          .loginPage("/")
          .loginProcessingUrl("/login")
          .failureUrl("/error-login")
          .defaultSuccessUrl("/process-login", true)
          .permitAll()
    .and()
      .logout()
          .logoutRequestMatcher(
              new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/user-logout")
    .and()
      .requiresChannel()
      .anyRequest()
      .requiresSecure()
    .and()
      .exceptionHandling()
          .accessDeniedPage("/forbidden");
    System.out.println("** Security Config Loaded **");
  }
  
  @Autowired 
  private UserDetailsService uds;  
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    auth.userDetailsService(uds).passwordEncoder(pe);
  }
}
