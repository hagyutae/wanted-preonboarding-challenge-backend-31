package com.preonboarding.util;

import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {
    private static String JWT_SECRET_KEY;

    @Value("${jwt.secret}")
    public void setJwtSecretKey(String jwtSecretKey) {
        JWT_SECRET_KEY = jwtSecretKey;
    }

    public static String createJwt(Long userId) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id", userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public static Long getUserId() throws BaseException {
        String accessToken = getJwt();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        return claims.get("id", Long.class);
    }

    private static String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (accessToken == null || accessToken.isEmpty()) {
            throw new BaseException(false, ErrorResponseDto.of(ErrorCode.INVALID_TOKEN_INPUT));
        }

        return accessToken.replaceAll("^Bearer( )*", "");
    }
}
