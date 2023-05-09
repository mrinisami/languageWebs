package app.sami.languageWeb.auth.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@AllArgsConstructor
public class JwtService {

    private JWKSet jwkSet;
    private String issuer;
    public Map<String, Object> getPublicKey(){
        return jwkSet.toJSONObject(true);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, new HashMap<>());
    }
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims){
        JWTClaimsSet claims = new JWTClaimsSet.Builder().jwtID(UUID.randomUUID().toString())
                .subject(userDetails.getUsername())
                .expirationTime(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)))
                .issueTime(Date.from(Instant.now()))
                .claim("roles", Arrays.asList("USER"))
                .issuer(issuer)
                .build();
        JWSHeader headers = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID(jwkSet.getKeys().get(0).getKeyID())
                .build();
        return sign(headers, claims);
    }

    private String sign(JWSHeader headers, JWTClaimsSet claims) {
        SignedJWT signedJWT = new SignedJWT(headers, claims);
        try {
            signedJWT.sign(new RSASSASigner(jwkSet.getKeys().get(0).toRSAKey().toRSAPrivateKey()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return signedJWT.serialize();
    }




}
