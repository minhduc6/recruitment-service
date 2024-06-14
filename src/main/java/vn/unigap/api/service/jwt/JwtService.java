package vn.unigap.api.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUserName(String token);
    Map<String, String> generateTokens(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
