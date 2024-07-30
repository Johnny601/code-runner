package com.evweet.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    private static final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private static final Long jwtExpiration = 1000L * 60 * 60 * 10; // 10 hours

    public static String generateToken(Long userId, Map<String, Object> extraClaims) {
        // claims need to be set before the subject
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static boolean isTokenValid(String token, Long userId) {
        String sub = extractClaim(token, Claims::getSubject);
        return (sub.equals(userId.toString())) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
