package app.sami.languageWeb.contract.dtos;

import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.request.dtos.RequestDto;
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
    private ContractStatus contractedContractStatus;
    private ContractStatus contractorContractStatus;
    private Long dueDate;

}
