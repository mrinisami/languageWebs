package app.sami.languageWeb.requestContributors.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RequestContributorFilter {
    private Long requestId;
    private UUID userId;
}
