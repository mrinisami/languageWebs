package app.sami.languageWeb.extensionRequest.dtos;

import app.sami.languageWeb.extensionRequest.models.ExtensionRequestStatus;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ExtensionRequestStatusDto {
    private ExtensionRequestStatus status;
}
