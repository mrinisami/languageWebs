package app.sami.languageWeb.contract.dtos;

import app.sami.languageWeb.contract.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractFilterDto {
    private List<Status> statuses;
    private UUID contractedUserId;
    private UUID requestUserId;
}
