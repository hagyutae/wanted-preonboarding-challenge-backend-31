package wanted.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {
    private final SecretKey secretKey;
    private final long accessExpiration;
    private final String issuer;

    public TokenProvider(@Value("${jwt.secret-key}") String secret_key,
                         @Value("${jwt.access-expiration}") long accessExpiration,
                         @Value("${jwt.issuer}") String issuer) {
        this.secretKey = new SecretKeySpec(secret_key.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessExpiration = accessExpiration;
        this.issuer = issuer;
    }

    public String getAccessToken(Long userId, String username) {
        return makeToken(makeClaims(userId), username, accessExpiration);
    }

    private Map<String, Object> makeClaims(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return claims;
    }

    private String makeToken(Map<String, Object> claims, String subject, Long expiry) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(secretKey)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Long getUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}