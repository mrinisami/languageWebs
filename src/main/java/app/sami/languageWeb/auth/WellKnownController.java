package app.sami.languageWeb.auth;

import app.sami.languageWeb.auth.services.JwtService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@AllArgsConstructor
@RestController
public class WellKnownController {
    private JwtService jwtService;
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys(){
        return jwtService.getPublicKey();
    }

    @GetMapping("/public/bonjour")
    public String getBonjour(){
        return "Bonjour";
    }

    @PostMapping(value = "/public/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@RequestBody MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        int info = 3 + 3;
    }
}
