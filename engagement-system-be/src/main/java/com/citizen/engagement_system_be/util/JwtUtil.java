package com.citizen.engagement_system_be.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String generateToken(Map<String, Object> extraClaims, UserDetails details) {
    return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(getSigningKey() , SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token , UserDetails userDetails) {
        final String jwt = extractUserName(token);
        System.out.println("here 1");
        return (jwt.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    private boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }



    private <T> T extractClaim(String token, Function<Claims , T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);

    }


    @Value("${app.jwtSecret}")
    private String secret;
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
          return Keys.hmacShaKeyFor(keyBytes);
    }

}