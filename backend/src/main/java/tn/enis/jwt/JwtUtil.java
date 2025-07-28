package tn.enis.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Le secret doit faire au moins 256 bits (soit 32 caractères)
    private static final String SECRET_KEY = "mySecretKeymySecretKeymySecretKey";

    // On génère une clé HMAC-SHA avec le secret
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public String generateToken(Long id, String fullName, String password) {
        return Jwts.builder()
                .claim("id", id)
                .claim("fullName", fullName)
                .setSubject(fullName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }


}
