package flatfinder.service;

import static flatfinder.FlatFinder.ROLE_ADMIN;
import static flatfinder.FlatFinder.ROLE_LANDLORD;
import static flatfinder.FlatFinder.ROLE_SEARCHER;
import static flatfinder.FlatFinder.ROLE_SUSPENDED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flatfinder.persistence.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    
  @Autowired
  private UserRepository userRepo;
  
  /**
   * Get current authentication and user details.
   **/
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    flatfinder.domain.User domainUser = userRepo.findByLogin(login);
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    return new User(
            domainUser.getLogin(), 
            domainUser.getPassword(), 
            enabled, 
            accountNonExpired, 
            credentialsNonExpired, 
            accountNonLocked,
            getAuthorities(domainUser.getRole().getId())
    );
  }
  
  private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
    List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
    return authList;
  }
  
  private List<String> getRoles(Integer role) {
    List<String> roles = new ArrayList<String>();
    if (role.intValue() == ROLE_ADMIN) {
      roles.add("ROLE_ADMIN");
    } else if (role.intValue() == ROLE_LANDLORD) {
      roles.add("ROLE_LANDLORD");
    } else if (role.intValue() == ROLE_SEARCHER) {
      roles.add("ROLE_SEARCHER");
    } else if (role.intValue() == ROLE_SUSPENDED) {
      roles.add("ROLE_SUSPENDED");
    }
    return roles;
  }
  
  private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
}
