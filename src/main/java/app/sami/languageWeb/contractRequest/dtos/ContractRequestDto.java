package app.sami.languageWeb.contractRequest.dtos;

import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ContractRequestDto {
    private Long id;
    private UUID userId;
    private Long requestId;
    private ContractRequestStatus status;
    private Instant createdAt;
}
