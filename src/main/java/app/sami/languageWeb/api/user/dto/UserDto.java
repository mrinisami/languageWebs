package app.sami.languageWeb.api.user.dto;

import app.sami.languageWeb.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private Date dateJoined;
}
