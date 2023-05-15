package app.sami.languageWeb.auth.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoopPasswordEncoderTests {

    private NoopPasswordEncoder noopPasswordEncoder;

    @BeforeEach
    void setup(){
        noopPasswordEncoder = new NoopPasswordEncoder();
    }

    @Test
    void matchingPassword_ReturnsTrue(){
        boolean result = noopPasswordEncoder.matches("bonjour", "bonjour");

        assertThat(result).isTrue();
    }

    @Test
    void notMatchingPassword_ReturnsTrue(){
        boolean result = noopPasswordEncoder.matches("bonjouur", "bonjour");

        assertThat(result).isFalse();
    }

}
