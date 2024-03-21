package com.a38c.eazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.a38c.eazybank.Util.Argon2Helper;
import com.a38c.eazybank.Util.JwtHelper;
import com.a38c.eazybank.filter.JwtTokenValidatorFilter;
import com.a38c.eazybank.filter.CsrfCookieFilter;
import com.a38c.eazybank.filter.JwtAuthenticationEntryPoint;
import com.a38c.eazybank.repository.UserRepository;
import com.a38c.eazybank.services.UserService;

import lombok.AllArgsConstructor;

import java.util.Collections;
import javax.sql.DataSource;

@ComponentScan(basePackages = "com.a38c.eazybank")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
@PropertySource("persistence-sqlite.properties")
public class ProjectSecurityConfig {

    private final UserRepository userRepository;
    private final Environment env;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        return http
            .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","/register")
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new JwtTokenValidatorFilter(userService()), BasicAuthenticationFilter.class)
            .exceptionHandling(
                exception -> exception.authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/notices","/contact","/register").permitAll()
                    .requestMatchers("/api/test/**").permitAll()
                    .anyRequest().authenticated()
            )

            .authenticationProvider(authenticationProvider())
            .cors(cors -> corsConfigurationSource())
                    // .formLogin()
                    // .httpBasic()
            .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new JwtAccessDeniedHandler();
    }

    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        // config.setAllowedOrigins(Collections.singletonList("https://a38c.com"));
		configuration.setAllowedOrigins(Collections.singletonList("https://localhost:3001"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		configuration.setAllowedMethods(Collections.singletonList("GET"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

    @Bean
    public AuthenticationProvider authenticationProvider() {
        UserPasswordAuthenticationProvider authenticationProvider = new UserPasswordAuthenticationProvider(userRepository, passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName", ""));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }

    @Bean
    public JwtHelper jwtTokenUtil() throws Exception {
        return new JwtHelper();
    }

    @Bean
    public Argon2Helper passwordEncoder() {
        return new Argon2Helper();
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }
}
