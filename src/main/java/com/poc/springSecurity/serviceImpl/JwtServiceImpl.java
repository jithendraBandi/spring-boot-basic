package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.poc.springSecurity.utils.Constants.SECRET_KEY;

@Service
public class JwtServiceImpl implements JwtService {
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

    private SecretKey getKey() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//        SecretKey sk = keyGenerator.generateKey();

//        byte[] keyBytes = sk.getEncoded();     // Can't use secretKey so that we can't decode while verifying.
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        byte[] keyBytes = SECRET_KEY.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String jwtToken) throws NoSuchAlgorithmException {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) throws NoSuchAlgorithmException {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) throws NoSuchAlgorithmException {
        return Jwts.parser()
                .verifyWith(getKey()).build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    @Override
    public boolean validateToken(String jwtToken, UserDetails userDetails) throws NoSuchAlgorithmException {
        String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) throws NoSuchAlgorithmException {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) throws NoSuchAlgorithmException {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
