package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.ContractDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.request.mapper.RequestMapper;

public class ContractMapper {

    public static ContractDto toContractDto(Contract contract){
        return ContractDto.builder()
                .contractedUserId(contract.getContractedUserId())
                .id(contract.getId())
                .requestDto(RequestMapper.toRequestDto(contract.getRequest()))
                .createdAt(contract.getCreatedAt())
                .modifiedAt(contract.getModifiedAt())
                .filePath(contract.getFilePath())
                .build();
    }
}
