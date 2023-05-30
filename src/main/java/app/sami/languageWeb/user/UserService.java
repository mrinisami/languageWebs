package app.sami.languageWeb.user;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Builder
public class UserService {

    private final UserRepository userRepository;
    public User getUserInfo(UUID userId){
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }
}
