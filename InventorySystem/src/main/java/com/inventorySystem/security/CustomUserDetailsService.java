package com.inventorySystem.security;

import com.inventorySystem.Model.User;
import com.inventorySystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            // No roles or authorities, just return the username and password
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }


}

