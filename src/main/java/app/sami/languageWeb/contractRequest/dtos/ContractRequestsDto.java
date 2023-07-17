package app.sami.languageWeb.contractRequest.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class ContractRequestsDto {
    private List<ContractRequestDto> contractRequests;
}
