package app.sami.languageWeb.requestContributors.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RequestsContributorDto {
    private List<RequestContributorDto> requestContributors;
}
