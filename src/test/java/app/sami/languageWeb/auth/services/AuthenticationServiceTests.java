package app.sami.languageWeb.auth.services;

import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.auth.dtos.AuthenticationRequest;
import app.sami.languageWeb.auth.dtos.RegisterRequest;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

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

    @Test
    void registerUser_ReturnsToken(){
        User user = userRepository.save(UserFactory.userGenerator().withUserRole(Role.USER));
        RegisterRequest request = RegisterRequest.builder()
                        .email(user.getEmail())
                                .userPassword(user.getUserPassword())

                                        .build();
        String result = registerNewUserService.register(request).getToken();

        assertThat(result).isNotEmpty();
    }

    @Test
    void givenExistingUserAuthuser_ReturnsToken(){
        User user = userRepository.save(UserFactory.userGenerator().withUserRole(Role.USER));
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(user.getEmail())
                .userPassword(user.getUserPassword())
                .build();
        String result = authenticateUserService.authenticate(authenticationRequest).getToken();

        assertThat(result).isNotEmpty();
    }

    @Test
    void givenWrongUserAuthuser_ThrowsBadCredentials(){
        User user = userRepository.save(UserFactory.userGenerator().withUserRole(Role.USER));
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(user.getEmail())
                .userPassword(Randomize.name())
                .build();

        assertThrows(BadCredentialsException.class, () ->
                authenticateUserService.authenticate(authenticationRequest));
    }
}
