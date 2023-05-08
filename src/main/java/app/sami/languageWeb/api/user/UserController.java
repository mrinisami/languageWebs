package app.sami.languageWeb.api.user;

import app.sami.languageWeb.api.user.dto.UserDto;
import app.sami.languageWeb.api.user.dto.UsersDto;
import app.sami.languageWeb.models.User;
import app.sami.languageWeb.repos.UserRepository;
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
    public UsersDto getUsers(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "2") int pageSize
    ) {
        UsersDto users = new UsersDto();
        List<UserDto> userDtos = userRepository.findAll(PageRequest.of(page, pageSize)).stream().map(this::toUserDto).collect(Collectors.toList());
        users.setUsers(userDtos);
        return users;
    }

    private UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }

}
