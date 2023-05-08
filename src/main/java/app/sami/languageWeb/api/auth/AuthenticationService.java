package app.sami.languageWeb.api.auth;

import app.sami.languageWeb.config.JwtService;
import app.sami.languageWeb.models.Role;
import app.sami.languageWeb.models.User;
import app.sami.languageWeb.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder().firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .email(request.getEmail())
                .userRole(Role.USER)
                .build();
        var jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getUserPassword()
        ));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
