package vn.unigap.api.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);

  @Value("${token.signing.key}")
  private String jwtSigningKey;

  @Value("${token.refresh.expiration}")
  private long refreshTokenExpiration;

  @Value("${token.access.expiration}")
  private long accessTokenExpiration;

  @Override
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Map<String, String> generateTokens(UserDetails userDetails) {
    String accessToken = generateAccessToken(userDetails.getUsername());
    String refreshToken = generateRefreshToken(userDetails.getUsername());
    Map<String, String> tokens = new HashMap<>();
    tokens.put("accessToken", accessToken);
    tokens.put("refreshToken", refreshToken);
    return tokens;
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  public String refreshAccessToken(String refreshToken) {
    if (isTokenExpired(refreshToken)) {
      throw new JwtException("Refresh token has expired");
    }
    final String userName = extractUserName(refreshToken);
    return generateAccessToken(userName);
  }

  private String generateAccessToken(String username) {
    return generateToken(new HashMap<>(), username, accessTokenExpiration);
  }

  private String generateRefreshToken(String username) {
    return generateToken(new HashMap<>(), username, refreshTokenExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, String username, long expiration) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (JwtException ex) {
      log.error("Jump Exception" + ex.getMessage());
      throw ex;
    }
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
