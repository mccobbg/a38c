package com.a38c.eazybank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.model.User;
import com.a38c.eazybank.payload.request.LoginRequest;
import com.a38c.eazybank.payload.response.LoginResponse;
import com.a38c.eazybank.repository.UserRepository;
import com.a38c.eazybank.util.Argon2Helper;
import com.a38c.eazybank.util.JwtHelper;

import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final Argon2Helper passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User savedCustomer = null;
        ResponseEntity<String> response = null;
        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            UUID uuid = UUID.randomUUID();
            user.setUserId(uuid.toString());
            user.setCreatedAt(new Date(System.currentTimeMillis()));
            savedCustomer = userRepository.save(user);
            if (savedCustomer.getEmail() != null) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                    )
                );

            User user = (User) authenticate.getPrincipal();
            String jwtToken = JwtHelper.createJWT(user);

            LoginResponse loginResponse = new LoginResponse(jwtToken, user.getEmail());

            return ResponseEntity.ok()
                .body(loginResponse);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @RequestMapping("/user")
    public User getUser(Authentication authentication) {
        List<User> users = userRepository.findByEmail(authentication.getName());
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
