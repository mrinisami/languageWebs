package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;

public class ExtensionRequestMapper {

    public static ExtensionRequestDto toExtensionRequestDto(ExtensionRequest extensionRequest){
        return ExtensionRequestDto.builder()
                .date(extensionRequest.getProposedDate().toEpochMilli())
                .contractId(extensionRequest.getContractId())
                .status(extensionRequest.getStatus())
                .id(extensionRequest.getId())
                .build();
    }
}
