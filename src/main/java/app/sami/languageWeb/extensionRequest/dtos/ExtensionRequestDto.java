package app.sami.languageWeb.extensionRequest.dtos;

import app.sami.languageWeb.extensionRequest.models.ExtensionRequestStatus;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ExtensionRequestDto {
    private Long date;
    private Long contractId;
    private ExtensionRequestStatus status;
    private Long id;
}
