package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
//    private String secretKey = "sfLHUjI57U6ikjh87KUG7iuggikUYIH";
//    private String secretKey;

    @Override
    public String generateToken(String username) throws NoSuchAlgorithmException {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims().add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 *  60 * 60 * 24)) // one day expiry
                .and().signWith(getKey())
                .compact();
    }

    private Key getKey() throws NoSuchAlgorithmException {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);  // 64 bits not secure
//        byte[] keyBytes = secretKey.getBytes();   // 248 bits not secure
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGenerator.generateKey();
        byte[] keyBytes = sk.getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
