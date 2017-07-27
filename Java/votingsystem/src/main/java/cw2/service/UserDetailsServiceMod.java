package cw2.service;

import static cw2.Cw2Application.ROLE_ERO;
import static cw2.Cw2Application.ROLE_VOTER;

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

import cw2.persistence.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceMod implements UserDetailsService {
    
  @Autowired
  private UserRepository userRepo;
  
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    cw2.domain.User domainUser = userRepo.findByUsername(username);
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    return new User(
            domainUser.getUsername(), 
            domainUser.getPassword(), 
            enabled, 
            accountNonExpired, 
            credentialsNonExpired, 
            accountNonLocked,
            getAuthorities(domainUser.getRole())
    );
  }
  
  private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
    List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
    return authList;
  }
  
  private List<String> getRoles(Integer role) {
    List<String> roles = new ArrayList<String>();
    if (role.intValue() == ROLE_ERO) {
      roles.add("ROLE_ERO");
    } else if (role.intValue() == ROLE_VOTER) {
      roles.add("ROLE_VOTER");
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
