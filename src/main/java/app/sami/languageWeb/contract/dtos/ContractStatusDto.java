package app.sami.languageWeb.contract.dtos;

import app.sami.languageWeb.contract.models.Status;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ContractStatusDto {
    private Status status;
}
