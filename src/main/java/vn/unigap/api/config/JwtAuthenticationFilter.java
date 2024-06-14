package vn.unigap.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.jwt.JwtService;
import vn.unigap.api.service.user.UserService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private static final String[] PUBLIC_ENDPOINTS = {"/auth/**"};

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(-1, HttpStatus.UNAUTHORIZED.value(), "Authentication failed: " + e.getMessage(), null);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        for (String endpoint : PUBLIC_ENDPOINTS) {
            if (new AntPathMatcher().match(endpoint, request.getServletPath())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Check if the request is to a public endpoint
        if (isPublicEndpoint(request)) {
            // If it's a public endpoint, just continue the filter chain without checking the token
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.split(" ")[1].trim();
            System.out.println("jwt: " + jwt);
            userEmail = jwtService.extractUserName(jwt);
            System.out.println("email: " + userEmail);
            if (StringUtils.isNotEmpty(userEmail)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService()
                        .loadUserByUsername(userEmail);
                System.out.println("R");
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("zo zo");
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

}
