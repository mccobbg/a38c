package com.a38c.eazybank.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.a38c.eazybank.model.User;
import com.a38c.eazybank.repository.UserRepository;
import com.a38c.eazybank.util.Argon2Helper;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final Argon2Helper passwordEncoder;

    public UserPasswordAuthenticationProvider(UserRepository userRepository, Argon2Helper passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<User> users = userRepository.findByUsername(username);
        if (users.size() == 0) {
            throw new BadCredentialsException("No user registered with this details!");
        }
        User user = users.get(0);
        boolean verified = false;
        try {
            verified = passwordEncoder.verify(pwd, user.getPassword());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid password!");
        }
        if (verified) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));         
            return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
