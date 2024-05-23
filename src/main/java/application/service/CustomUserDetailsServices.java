package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import application.model.User;
import application.repo.UserRepository;
import jakarta.persistence.NonUniqueResultException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServices implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        Set<String> roles = user.getRoles(); 
        Collection<? extends GrantedAuthority> authorities = getAuthorities(roles);
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getDepartment(),
                roles
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
}
