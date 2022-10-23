package az.et.unitech.common.security;

import az.et.unitech.common.error.exception.CommonAuthException;
import az.et.unitech.common.model.enums.UserType;
import az.et.unitech.common.security.model.CustomUserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private Key key;
    private static final List<GrantedAuthority> AUTHORITIES = List.of(new SimpleGrantedAuthority("USER"));

    @Value("${application.security.authentication.jwt.base64-secret:}")
    private String base64Secret;


    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public <T extends CommonAuthException> void validateToken(String token, Supplier<T> exceptionSupplier) {
        if (!isValidToken(token)) throw exceptionSupplier.get();
    }

    public boolean isValidToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.warn("JWT token compact of handler are invalid");
        } catch (JwtException e) {
            log.warn("Invalid JWT token");
        }
        return false;
    }

    public Authentication parseAuthentication(String authToken) {
        validateToken(authToken, () -> new CommonAuthException(HttpStatus.UNAUTHORIZED.value(), "Invalid token!"));
        final Claims claims = extractClaim(authToken);
        final User principal = getPrincipal(claims, AUTHORITIES);
        return new UsernamePasswordAuthenticationToken(principal, authToken, AUTHORITIES);
    }

    private Claims extractClaim(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    private CustomUserPrincipal getPrincipal(Claims claims, Collection<? extends GrantedAuthority> authorities) {
        String subject = claims.getSubject();
        String tokenType = claims.get(TokenKey.TOKEN_TYPE, String.class);
        String fullName = claims.get(TokenKey.FULL_NAME, String.class);
        UserType userType = UserType.valueOf(claims.get(TokenKey.USER_TYPE, String.class));
        return new CustomUserPrincipal(subject, fullName, tokenType, "USER", userType, authorities);
    }

}