package app.sami.languageWeb.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class RequestJwtClaims {
    private final Map<String, Object> claims;

    public static RequestJwtClaims of(Map<String, Object> claims){
        return new RequestJwtClaims(claims);
    }

    public Optional<UUID> getSubject() {return claimToUUID("sub");}
    public Optional<String> claimToString(String key){
        return rawClaim(key).map(Object::toString);
    }

    public Optional<UUID> claimToUUID(String key){
        return rawClaim("sub").map((claim) -> UUID.fromString(claim.toString()));
    }
    public Optional<Object> rawClaim(String key){
        return Optional.ofNullable(claims.getOrDefault(key, null));
    }
}
