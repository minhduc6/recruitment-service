package vn.unigap.api.service.jwt;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUserName(String token);

  Map<String, String> generateTokens(UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);
}
