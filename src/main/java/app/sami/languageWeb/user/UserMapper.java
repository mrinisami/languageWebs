package app.sami.languageWeb.user;

import app.sami.languageWeb.user.dto.UserDto;
import app.sami.languageWeb.user.models.User;

public class UserMapper {

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarUri(user.getAvatarUri())
                .build();
    }
}
