package app.sami.languageWeb.auth.services;

import app.sami.languageWeb.user.models.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;

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
    public String generateToken(User user){
        return generateToken(user, new HashMap<>());
    }
    public String generateToken(User user, Map<String, Object> extraClaims){
        JWTClaimsSet claims = new JWTClaimsSet.Builder().jwtID(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("userId", user.getId())
                .expirationTime(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)))
                .issueTime(Date.from(Instant.now()))
                .claim("roles", user.getRole().impliedRoles(user.getRole()))
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
