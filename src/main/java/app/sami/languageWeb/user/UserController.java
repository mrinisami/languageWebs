package app.sami.languageWeb.user;

import app.sami.languageWeb.user.repos.UserRepository;
import app.sami.languageWeb.user.dto.UserDto;
import app.sami.languageWeb.user.dto.UsersDto;
import app.sami.languageWeb.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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
