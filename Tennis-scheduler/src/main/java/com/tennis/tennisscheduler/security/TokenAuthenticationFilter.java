package com.tennis.tennisscheduler.security;

import com.tennis.tennisscheduler.services.impl.CustomUserDetailsServiceImpl;
import com.tennis.tennisscheduler.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final CustomUserDetailsServiceImpl userDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String email;
        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            email = tokenUtils.getEmailFromToken(authToken);
            if (email != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (tokenUtils.validateToken(authToken, userDetails)) {
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
