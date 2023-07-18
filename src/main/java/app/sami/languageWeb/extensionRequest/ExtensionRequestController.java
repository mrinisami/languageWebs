package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestStatusDto;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequestStatus;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ExtensionRequestController {

    private final ExtensionRequestRepository extensionRequestRepository;
    private final ExtensionRequestService extensionRequestService;
    private final ContractRepository contractRepository;

    @PostMapping("/extension-requests")
    public ExtensionRequestDto createExtensionRequest(
                                                      @RequestJwtSubject UUID subject,
                                                      @RequestBody ExtensionRequestDto extensionRequestDto){
        Contract contract = contractRepository.findById(extensionRequestDto.getContractId()).orElseThrow(NotFoundException::new);

        if (!contract.isContracted(subject)){
            throw new UserNotAllowedException("Only the contracted user can request for an extension.");
        }

        ExtensionRequest extension = extensionRequestRepository.save(ExtensionRequest.builder()
                .contractId(extensionRequestDto.getContractId())
                .userId(subject)
                .proposedDate(Instant.ofEpochMilli(extensionRequestDto.getDate()))
                .status(ExtensionRequestStatus.PENDING)
                .build());

        return ExtensionRequestMapper.toExtensionRequestDto(extension);
    }

    @PutMapping("/extension-requests/{extensionRequestId}")
    public ExtensionRequestDto updateStatus(
                                            @PathVariable Long extensionRequestId,
                                            @RequestJwtSubject UUID subject,
                                            @RequestBody ExtensionRequestStatusDto extensionRequestStatusDto){


        return ExtensionRequestMapper.toExtensionRequestDto(extensionRequestService.updateStatus(
                subject, extensionRequestId, extensionRequestStatusDto
        ));
    }

    @GetMapping("/extension-requests")
    public ExtensionRequestDto getExtensionRequest(@RequestParam Long contractId,
                                                   @RequestJwtSubject UUID subject){
        ExtensionRequest extensionRequest = extensionRequestRepository.findFirstByContractIdAndStatusOrderByModifiedAtDesc(
                contractId,
                ExtensionRequestStatus.PENDING).orElseThrow(NotFoundException::new);
        if (!extensionRequest.isContractor(subject)){
            throw new UserNotAllowedException("Only the requester can see extension requests.");
        }
        return ExtensionRequestMapper.toExtensionRequestDto(extensionRequest);
    }
}
