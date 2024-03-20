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
import com.a38c.eazybank.model.Role;
import com.a38c.eazybank.model.User;
import com.a38c.eazybank.repository.UserRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        boolean verified = false;
        try {
            verified = passwordEncoder.verify(pwd, users.get(0).getPassword());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid password!");
        }
        if (verified) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            Set<Role> roles = users.get(0).getRoles();
            Iterator<Role> rolesIt = roles.iterator();
            while (rolesIt.hasNext()) {
                Role role = rolesIt.next();
                authorities.add(new SimpleGrantedAuthority(role.toString()));
            }
            
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
