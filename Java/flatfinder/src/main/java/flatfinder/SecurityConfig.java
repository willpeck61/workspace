package flatfinder;

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
    http
    .authorizeRequests()
    .antMatchers("/user-registration", "/user-login", "/error-message", "/logout")
    .anonymous()
    .and()
    .authorizeRequests()
    .antMatchers("/", "/view-profile", "/message", "/notification",
        "/message/view/**", "/message/create/", "logout", "/upload",
        "/add-interest", "/get-users", "/remove-interest", "/buddy-request",
        "/buddy-search", "/view-buddy-request", "/amend-profile", "/message/create",
        "/notifications", "/property", "/search", "/user", "/property/report", "/user/report",
        "/submitPropertyReport", "/submitUserReport", "/remove-buddy",
        "/amend-user","/submitForgotPassword", "/action-buddy-request",
        "/submitFeedback", "/InterestedProperty", "/removeInterestedProperty",
        "/message", "/message/view",
        "/sendInitMessage", "/inbox", "/inbox/view",
        "/view-buddy-profile", "/submitEditedProperty")
    .hasAnyRole("SEARCHER", "LANDLORD", "ADMIN")
    .and()
    .authorizeRequests()
    .antMatchers("/landlord-dashboard/**",
        "/landlord-dashboard/manage/**", "/landlord-dashboard/new/**",
        "/submitAddedRoom", "/submitAddedProperty", "/submitAddedPropertyPWOI",
        "/submitEditedPropertyPWOI"
     )
    .hasRole("LANDLORD")
    .and()
    .authorizeRequests()
    .antMatchers("/admin-dashboard/**",
        "/admin-dashboard/users/**", "/admin-dashboard/listings/**")
    .hasRole("ADMIN")
    .and()
    .formLogin()
    .loginPage("/user-login")
    .loginProcessingUrl("/login")
    .failureUrl("/error-login")
    .permitAll()
    .and()
    .logout()
    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    .logoutSuccessUrl("/user-logout")
    .and()
    .requiresChannel()
    .anyRequest()
    .requiresSecure()
    .and()
    .exceptionHandling().accessDeniedPage("/user-error");
    
    http.authorizeRequests().antMatchers("/amend-user").hasAnyRole("ADMIN", "SEARCHER", "LANDLORD");
    http.authorizeRequests().antMatchers("/resources/**").permitAll();
    http.authorizeRequests().antMatchers("/resources/css/**").permitAll();
    http.authorizeRequests().antMatchers("/resources/script/**").permitAll();
    http.authorizeRequests().antMatchers("/resources/js/**").permitAll();
    http.authorizeRequests().antMatchers("/resources/img/**").permitAll();
    http.authorizeRequests().antMatchers("/privacy").permitAll();
    http.authorizeRequests().antMatchers("/terms").permitAll();
    http.authorizeRequests().antMatchers("/js/**").permitAll();
    http.authorizeRequests().antMatchers("/img/**").permitAll();
    http.authorizeRequests().antMatchers("/forgotPassword").permitAll();
    http.authorizeRequests().antMatchers("http://maps.googleapis.com/maps/**").permitAll();
    http.authorizeRequests().antMatchers("http://a.tile.openstreetmap.org/**").permitAll();
  }

  @Autowired 
  private UserDetailsService userDetailsService;  
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
    auth.userDetailsService(userDetailsService).passwordEncoder(pe);
  }
}               
