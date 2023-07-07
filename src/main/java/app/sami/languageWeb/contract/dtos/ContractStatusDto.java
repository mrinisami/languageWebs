package app.sami.languageWeb.contract.dtos;

import app.sami.languageWeb.contract.models.ContractStatus;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ContractStatusDto {
    private ContractStatus contractStatus;
}
