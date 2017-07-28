package sciConsole;

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
                // the access to THE only service is
                // available to all users
                .antMatchers("/")
                	.hasAnyRole("MANAGER","DEVELOPER")
                .antMatchers("/resources/css/**").permitAll()
                .antMatchers("/resources/script/**").permitAll()
                .antMatchers("/assess-change-request*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/user-login") 
                .defaultSuccessUrl("/success-login",true)
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
    }
    
    @Autowired 
    private UserDetailsService userDetailsService;  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(pe);
    }
}               
