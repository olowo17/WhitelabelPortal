package lazyprogrammer.jwtdemo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.dtos.TokenUser;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.mappers.PortalUserMapper;
import lazyprogrammer.jwtdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class JwtUtil {
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.lifespan}")
    private long tokenLifeSpan;

    private static final String SECRET_KEY = "T@K@N3C_P"; // Replace with your actual secret

    // Method to extract and decode the JWT token and return the TokenUser
    public TokenUser getTokenUserFromRequest(HttpServletRequest request) throws Exception {
        String token = extractTokenFromHeader(request);
        String username = extractUsernameFromToken(token);
        Optional<PortalUser> portalUser = userRepository.findByUsername(username);
        return PortalUserMapper.mapEntityToTokenUser(portalUser.get());
    }

    // Method to extract the JWT token from the request headers
    private String extractTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization"); // Or request.getHeader("sessionID");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        return token.substring(7); // Remove "Bearer " prefix
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String generateToken(PortalUser portalUser) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, portalUser.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<PortalUser> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with username: " + username);
        }

        PortalUser user = optionalUser.get();

        // Serializing the user object to JSON
        String userJson;
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize user object to JSON", e);
        }

        // Adding the serialized user JSON to claims
        claims.put("user", userJson);

        // Creating the JWT token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifeSpan))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateToken(Map<String, Object> extraClaims, PortalUser portalUser) {
        Long userId = portalUser.getId();
        String email = portalUser.getEmail();
        extraClaims.put("userId", userId);
        return createToken(extraClaims, portalUser.getUsername());
    }

//    private String createToken(Map<String, Object> claims, String username) {
//        return Jwts.builder().claims(claims)
//                .subject(username)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + tokenLifeSpan))
//                .signWith(getSigningKey())
//                .compact();
//    }

    private Claims extractAllClaims(String token) {
        //return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
