package com.a38c.eazybank.config;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.a38c.eazybank.constants.ApplicationConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
        
   // public static final Logger LOG = Logger.getLogger(JWTAccessDeniedHandler.class);

    @Override
    public void handle(
      HttpServletRequest request,
      HttpServletResponse response, 
      AccessDeniedException exc) throws IOException, ServletException {
        
        Authentication auth 
          = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            // LOG.warn("User: " + auth.getName() 
            //   + " attempted to access the protected URL: "
            //   + request.getRequestURI());
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, ApplicationConstants.ERROR_403_MSG);
    }

}
