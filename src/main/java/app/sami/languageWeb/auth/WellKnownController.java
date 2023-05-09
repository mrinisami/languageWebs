package app.sami.languageWeb.auth;

import app.sami.languageWeb.auth.services.JwtService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
public class WellKnownController {
    private JwtService jwtService;
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys(){
        return jwtService.getPublicKey();
    }

    @GetMapping("/bonjour")
    @RolesAllowed(Role.Raw.ADMIN)
    public String getBonjour(){
        return "Bonjour";
    }
}
