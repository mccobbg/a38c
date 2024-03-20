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

import com.a38c.eazybank.Util.Argon2Helper;
import com.a38c.eazybank.Util.JwtHelper;
import com.a38c.eazybank.model.User;
import com.a38c.eazybank.payload.request.LoginRequest;
import com.a38c.eazybank.payload.response.LoginResponse;
import com.a38c.eazybank.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final Argon2Helper passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    // @GetMapping("/authorities")
    // public Map<String,Object> getPrincipalInfo(JwtAuthenticationToken principal) {
        
    //     Collection<String> authorities = principal.getAuthorities()
    //       .stream()
    //       .map(GrantedAuthority::getAuthority)
    //       .collect(Collectors.toList());
        
    //     Map<String,Object> info = new HashMap<>();
    //     info.put("name", principal.getName());
    //     info.put("authorities", authorities);
    //     info.put("tokenAttributes", principal.getTokenAttributes());

    //     if ( principal instanceof AccountToken ) {
    //       info.put( "account", ((AccountToken)principal).getAccount());
    //     }

    //     return info;
    // }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User savedCustomer = null;
        ResponseEntity<String> response = null;
        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            user.setCreatedDt(new Date(System.currentTimeMillis()));
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
            String username = user.getEmail();
            String jwtToken = JwtHelper.createJWT(username);

            LoginResponse loginResponse = new LoginResponse(jwtToken, username);

            return ResponseEntity.ok()
                .body(loginResponse);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @RequestMapping("/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        List<User> users = userRepository.findByUsername(authentication.getName());
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    // @GetMapping("/authorities")
    // public Map<String,Object> getPrincipalInfo(JwtAuthenticationToken principal) {
        
    //     Collection<String> authorities = principal.getAuthorities()
    //       .stream()
    //       .map(GrantedAuthority::getAuthority)
    //       .collect(Collectors.toList());
        
    //     Map<String,Object> info = new HashMap<>();
    //     info.put("name", principal.getName());
    //     info.put("authorities", authorities);
    //     info.put("tokenAttributes", principal.getTokenAttributes());

    //     if ( principal instanceof AccountToken ) {
    //       info.put( "account", ((AccountToken)principal).getAccount());
    //     }

    //     return info;
    // }

    /*
    @PostMapping("/api/v1/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
        return JwtResponseDTO.builder()
                .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
     */
    
}
