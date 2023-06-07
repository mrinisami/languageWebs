package app.sami.languageWeb.auth.services;

import app.sami.languageWeb.auth.AuthenticationResponse;
import app.sami.languageWeb.auth.dtos.AuthenticationRequest;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        /*authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getUserPassword()
        ));*/
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(NotFoundException::new
                );
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
