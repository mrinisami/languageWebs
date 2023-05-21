package app.sami.languageWeb.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Builder
public class RequestJwtClaims {
    private final Map<String, Object> claims;

    public static RequestJwtClaims of(Map<String, Object> claims){
        return new RequestJwtClaims(claims);
    }

    public Optional<String> getSubject(){
        return claimToString("sub");
    };

    public Optional<String> claimToString(String key){
        return rawClaim(key).map(Object::toString);
    }

    public Optional<Object> rawClaim(String key){
        return Optional.ofNullable(claims.getOrDefault(key, null));
    }
}
