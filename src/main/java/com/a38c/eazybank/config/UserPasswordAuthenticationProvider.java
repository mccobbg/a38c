package com.a38c.eazybank.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.a38c.eazybank.Util.Argon2Helper;
import com.a38c.eazybank.model.User;
import com.a38c.eazybank.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

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
            List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
            
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
