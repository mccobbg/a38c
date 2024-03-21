package com.a38c.eazybank.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.a38c.eazybank.model.User;
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
        return UserDetailsImpl.build(users.get(0));
	}

    public void saveUser(@NonNull User user) {
        userRepository.save(user);
    }
}
