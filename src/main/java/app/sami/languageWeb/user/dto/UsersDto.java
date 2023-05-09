package app.sami.languageWeb.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UsersDto {
    private List<UserDto> users;

}
