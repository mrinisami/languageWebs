package app.sami.languageWeb.config;

import app.sami.languageWeb.auth.services.NoopPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public PasswordEncoder noopEncoder(){
        return new NoopPasswordEncoder();
    }
}
