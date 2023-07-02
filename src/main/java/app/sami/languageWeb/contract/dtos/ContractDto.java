package app.sami.languageWeb.contract.dtos;

import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.models.Request;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ContractDto {
    private Long id;
    private UUID contractedUserId;
    private RequestDto requestDto;
    private String filePath;
    private Instant createdAt;
    private Instant modifiedAt;
}
