package app.sami.languageWeb.contractRequest.dtos;

import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class StatusDto {
    private ContractRequestStatus status;
}
