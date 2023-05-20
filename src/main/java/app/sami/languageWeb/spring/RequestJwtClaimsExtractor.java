package app.sami.languageWeb.spring;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestJwtClaimsExtractor {
    public Optional<RequestJwtClaims> extract(){
        return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal).filter(p -> p instanceof Jwt).map(p -> (Jwt) p).map(Jwt::getClaims)
                .map(RequestJwtClaims::of);
    }
}
