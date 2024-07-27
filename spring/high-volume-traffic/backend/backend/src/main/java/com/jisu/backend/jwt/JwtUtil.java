package com.jisu.backend.jwt;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  // @Value("${jwt.secret}")
  private String secretKey = "LBh-rrv4iJjAzNGIKwP0f7-OMnNLdEcbUCsxyYevvrI=";

  // @Value("${jwt.expiration}")
  private long expiration = 3600000L;

  public String generateToken(String username) {
    Date date = new Date();
    Date expirationDate = new Date(date.getTime() + expiration);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(date)
        .setExpiration(expirationDate)
        .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token)
        .getBody().getSubject();
  }

  public Date getExpirationDateFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token)
        .getBody().getExpiration();
  }
}
