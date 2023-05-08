package app.sami.languageWeb.api.auth.services;

import app.sami.languageWeb.api.auth.AuthenticationResponse;
import app.sami.languageWeb.api.auth.dtos.RegisterRequest;
import app.sami.languageWeb.models.Role;
import app.sami.languageWeb.models.User;
import app.sami.languageWeb.repos.UserRepository;
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
