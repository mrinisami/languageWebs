package app.sami.languageWeb.auth.services;

import app.sami.languageWeb.auth.AuthenticationResponse;
import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.dtos.RegisterRequest;

import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewUserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder().firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .email(request.getEmail())
                .userRole(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
