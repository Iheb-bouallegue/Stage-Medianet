package com.example.medianet.stagemedianet.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtils {
    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private long expirationTime;

    public String generateToken(Long userId, String username, String role,String email) {
        // Crée une carte des claims à partir des informations d'utilisateur
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);   // ID de l'utilisateur
        claims.put("username", username); // Nom d'utilisateur
        claims.put("email", email); // Email de l'utilisateur
        claims.put("role", role); // Rôle de l'utilisateur

        // Crée le token avec les claims
        return Jwts.builder()
                .setSubject(username)  // Le sujet est le nom d'utilisateur
                .claim("id", userId)  // Ajoute l'ID de l'utilisateur
                .claim("email", email)  // Ajoute l'email de l'utilisateur
                .claim("role", role)  // Ajoute le rôle de l'utilisateur
                .setIssuedAt(new Date())  // Date de création du token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Date d'expiration
                .signWith(SignatureAlgorithm.HS512, getSignKey())  // Signature avec la clé secrète
                .compact();
    }




    private Key getSignKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
