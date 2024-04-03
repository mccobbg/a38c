package com.a38c.eazybank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.constants.ApplicationConstants;
import com.a38c.eazybank.model.User;
import com.a38c.eazybank.payload.request.LoginRequest;
import com.a38c.eazybank.payload.request.RegisterRequest;
import com.a38c.eazybank.payload.response.LoginResponse;
import com.a38c.eazybank.repository.UserRepository;
import com.a38c.eazybank.services.UserDetailsImpl;
import com.a38c.eazybank.util.JwtHelper;

import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.List;
//import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        HttpStatusCode statusCode = HttpStatus.CREATED;
        String message = null;
        boolean emailExists = userRepository.existsByEmail(request.getEmail());
        if (emailExists) {
            statusCode = HttpStatus.BAD_REQUEST;
            message = ApplicationConstants.ERROR_400_MSG;
        } else {
            try {
                User user = new User();
                String hashPwd = passwordEncoder.encode(request.getPassword());
                user.setPassword(hashPwd);
                UUID uuid = UUID.randomUUID();
                user.setUserId(uuid.toString());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setRole("USER");
                user.setMobileNumber(request.getMobileNumber());
                user.setCreatedAt(new Date(System.currentTimeMillis()));
                User savedUser = userRepository.save(user);
                if (savedUser.getEmail() != null) {
                    return ResponseEntity
                            .status(statusCode)
                            .body("Given user details are successfully registered");
                }
            } catch (Exception ex) {
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
                message = ApplicationConstants.ERROR_500_MSG;
            }
        }

        return ResponseEntity
            .status(statusCode)
            .body(message);
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

            UserDetailsImpl user = (UserDetailsImpl) authenticate.getPrincipal();
            String jwtToken = jwtHelper.createJWT(user);

            LoginResponse loginResponse = new LoginResponse(jwtToken, user.getUserId());

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
