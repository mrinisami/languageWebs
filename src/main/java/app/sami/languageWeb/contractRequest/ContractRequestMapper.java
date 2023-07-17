package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contractRequest.dtos.ContractRequestDto;
import app.sami.languageWeb.contractRequest.models.ContractRequest;

public class ContractRequestMapper {
    public static ContractRequestDto toContractRequestDto(ContractRequest contractRequest){
        return ContractRequestDto.builder()
                .id(contractRequest.getId())
                .requestId(contractRequest.getRequestId())
                .status(contractRequest.getStatus())
                .userId(contractRequest.getUserId())
                .createdAt(contractRequest.getCreatedAt())
                .build();
    }
}
