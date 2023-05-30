package app.sami.languageWeb.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private UUID id;

    private String firstName;
    private String lastName;
    private Date dateJoined;
}
