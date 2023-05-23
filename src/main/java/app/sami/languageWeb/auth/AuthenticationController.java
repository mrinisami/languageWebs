package app.sami.languageWeb.auth;

import app.sami.languageWeb.auth.services.RegisterNewUserService;
import app.sami.languageWeb.auth.dtos.AuthenticationRequest;
import app.sami.languageWeb.auth.dtos.RegisterRequest;
import app.sami.languageWeb.auth.services.AuthenticateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticateUserService service;
    private final RegisterNewUserService registerService;


    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return registerService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request){
        return service.authenticate(request);
    }
}
