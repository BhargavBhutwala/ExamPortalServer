package com.exam.examserver.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenHelper {


    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    private String secret = "sJtBQvEkXQ2mK6VsZjGyRpjJ7NEd6HL6hHg4fzzl2fs8TpZj5XV8GbOHSSJ59OwGQEpJvDsPnh4vNZX2sH9HOPL2y5my0FAdoCnZG2sCIvO8hU7ZwT6PHN4SjkZw7oCl"; 

    private SecretKey getSecretKey() {
        // byte[] keyBytes = Decoders.BASE64.decode(secret);
        // return Keys.hmacShaKeyFor(keyBytes);
        //return Jwts.SIG.HS512.key().build(); 
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        //return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        SecretKey secretKey = getSecretKey();
        System.out.println("Secret Key (used for parsing): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        // return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        //         .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        //         .signWith(SignatureAlgorithm.HS512, secret).compact();

        SecretKey secretKey = getSecretKey();
        System.out.println("Secret Key (used for signing): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	
}