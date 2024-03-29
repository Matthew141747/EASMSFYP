package com.fypp.Ethics_Management_System.UserResLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert the accountType to GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("faculty".equals(user.getAccountType())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_FACULTY"));
        } else if ("admin".equals(user.getAccountType())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // Return a User object (from Spring Security) with authorities
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}