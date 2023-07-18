package app.sami.languageWeb.requestContributors.dtos;

import app.sami.languageWeb.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PostRequestContributor {
    private Long id;
    private Double contribution;
}
