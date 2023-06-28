package app.sami.languageWeb.auth.services;

import app.sami.languageWeb.auth.AuthenticationResponse;
import app.sami.languageWeb.auth.dtos.AuthenticationRequest;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(NotFoundException::new
                );
        if (!passwordEncoder.matches(request.getUserPassword(), user.getPassword())){
            throw new BadCredentialsException("Wrong password-username combination");
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
