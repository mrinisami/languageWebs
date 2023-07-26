package app.sami.languageWeb.auth.services;

import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.auth.dtos.AuthenticationRequest;
import app.sami.languageWeb.auth.dtos.RegisterRequest;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.stripe.StripeAccount;
import app.sami.languageWeb.stripe.StripeAccountRepository;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationServiceTests extends IntegrationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;
    @Autowired
    RegisterNewUserService registerNewUserService;
    @Autowired
    AuthenticateUserService authenticateUserService;
    @Autowired
    StripeAccountRepository stripeAccountRepository;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }
    @Test
    void registerUserAndCreateStripeAccount_ReturnsTrue(){
        String email = Randomize.email();
        String password = Randomize.string("123");
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .userPassword(password)
                .isTranslator(true)
                .build();
        registerNewUserService.register(request);
        User user = userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
        Optional<StripeAccount> result = stripeAccountRepository.findByUserId(user.getId());

        assertThat(result).isPresent();
    }
    @Test
    void registerUserClient_ReturnsTrue(){
        String email = Randomize.email();
        String password = Randomize.string("123");
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .userPassword(password)
                .isTranslator(false)
                .build();
        registerNewUserService.register(request);
        User user = userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
        Optional<StripeAccount> result = stripeAccountRepository.findByUserId(user.getId());

        assertThat(result).isEmpty();
    }
    @Test
    void registerUser_ReturnsToken(){
        String email = Randomize.email();
        String password = Randomize.string("123");
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .userPassword(password)
                .isTranslator(false)
                .build();
        registerNewUserService.register(request);
        Optional<User> user = userRepository.findByEmail(email);

        assertThat(user).isNotEmpty();
    }

    @Test
    void givenExistingUserAuthuser_ReturnsToken(){
        User user = userRepository.save(UserFactory.userGenerator().withRole(Role.USER));
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(user.getEmail())
                .userPassword(user.getPassword())
                .build();
        String result = authenticateUserService.authenticate(authenticationRequest).getToken();

        assertThat(result).isNotEmpty();
    }

    @Test
    void givenWrongUserAuthuser_ThrowsBadCredentials(){
        User user = userRepository.save(UserFactory.userGenerator().withRole(Role.USER));
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(user.getEmail())
                .userPassword(Randomize.name())
                .build();

        assertThrows(BadCredentialsException.class, () ->
                authenticateUserService.authenticate(authenticationRequest));
    }
}
