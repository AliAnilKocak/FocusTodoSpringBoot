package com.cale.focustodo.security;

import com.cale.focustodo.dto.LoginResponseDto;
import com.cale.focustodo.entity.ApplicationUser;
import com.cale.focustodo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilService {
    private final String secret = "vhbsdhjbsdjhbreyvjhvbsdhjsvbjhsdvj";

    @Autowired
    UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUsernameFromRequest(String tokenHeader) {
        String token = tokenHeader.substring(7);
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public ResponseEntity<LoginResponseDto> generateToken(String username, ApplicationUser applicationUser) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username,applicationUser);
    }

    private ResponseEntity<LoginResponseDto> createToken(Map<String, Object> claims, String subject,ApplicationUser applicationUser) {

        ApplicationUser curentUser = userRepository.findByUsername(applicationUser.getUsername());

        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 100)) //TODO 1000 * 60 * 60 * 10
                .signWith(SignatureAlgorithm.HS256, secret).compact();

        return ResponseEntity.ok(new LoginResponseDto(token,curentUser.getId(),curentUser.getUsername(),curentUser.getEmail()));

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
