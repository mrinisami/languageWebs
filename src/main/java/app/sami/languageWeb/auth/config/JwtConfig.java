package app.sami.languageWeb.auth.config;

import app.sami.languageWeb.auth.services.JwtService;
import app.sami.languageWeb.props.AppProps;
import app.sami.languageWeb.props.JksProps;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.security.KeyStore;

@Configuration
public class JwtConfig {
    @Autowired
    private AppProps appProps;

   @Bean
    public JwtService jwtService() throws Exception {
        return new JwtService(jwkSet(), appProps.issuer);
    }

    private JWKSet jwkSet() throws Exception {
        return JWKSet.load(keystore(appProps.jks), passwordLookup(appProps.jks));
    }

    private KeyStore keystore(JksProps jksProps) throws Exception {
        File jwksFile = ResourceUtils.getFile(jksProps.filePath);

        return KeyStore.getInstance(jwksFile, jksProps.password.toCharArray());
    }

    private PasswordLookup passwordLookup(JksProps jksProps) {
        return (name) -> jksProps.password.toCharArray();
    }

}
