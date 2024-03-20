package com.a38c.eazybank.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import com.a38c.eazybank.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.a38c.eazybank.repository.UserRepository;

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    	
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        User user = users.get(0);
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            new ArrayList<>() //  user.getRole()
        );
	}

    public void saveUser(@NonNull User user) {
        userRepository.save(user);
    }
}
