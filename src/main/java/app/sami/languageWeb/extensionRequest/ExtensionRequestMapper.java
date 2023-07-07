package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;

public class ExtensionRequestMapper {

    public static ExtensionRequestDto toExtensionRequestDto(ExtensionRequest extensionRequest){
        return ExtensionRequestDto.builder()
                .date(extensionRequest.getProposedDate())
                .contractId(extensionRequest.getContractId())
                .build();
    }
}
