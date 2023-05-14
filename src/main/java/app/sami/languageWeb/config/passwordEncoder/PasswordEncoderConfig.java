package app.sami.languageWeb.config.passwordEncoder;

import app.sami.languageWeb.auth.services.NoopPasswordEncoder;
import app.sami.languageWeb.props.AppProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Autowired
    private AppProps appProps;
    @Bean
    public PasswordEncoder passwordEncoder() {
        if (appProps.passwordEncoder.equals("noop")){
            return new NoopPasswordEncoder();
        }
        return new BCryptPasswordEncoder();
    }
}
