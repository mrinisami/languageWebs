package app.sami.languageWeb.api.auth;

import app.sami.languageWeb.api.dto.UserDto;
import app.sami.languageWeb.models.User;
import app.sami.languageWeb.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "2") int pageSize
    ) {
        List<UserDto> users = userRepository.findAll(PageRequest.of(page, pageSize)).stream().map(this::toUserDto).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    private UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }

    private User toUser(UserDto userInfo) {
        User user = new User();
        user.setUserPassword(userInfo.getUserPassword());
        user.setEmail(userInfo.getEmail());
        user.setDateJoined(userInfo.getDateJoined());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());

        return user;
    }
}
