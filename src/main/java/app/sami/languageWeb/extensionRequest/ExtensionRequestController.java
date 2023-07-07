package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
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

    @PostMapping("/contracts/{contractId}/extension-request")
    public ExtensionRequestDto createExtensionRequest(@PathVariable Long contractId,
                                                      @RequestJwtSubject UUID subject,
                                                      @RequestBody Long proposedDate){
        ExtensionRequest extension = extensionRequestRepository.save(ExtensionRequest.builder()
                .contractId(contractId)
                .userId(subject)
                .proposedDate(Instant.ofEpochMilli(proposedDate))
                .status(ExtensionRequestStatus.PENDING)
                .build());

        return ExtensionRequestMapper.toExtensionRequestDto(extension);
    }

    @PutMapping("/contracts/{contractId}/extensionRequests/{extensionRequestId}")
    public ExtensionRequestDto updateStatus(@PathVariable Long contractId,
                                            @PathVariable Long extensionRequestId,
                                            @RequestJwtSubject UUID subject,
                                            @RequestBody ExtensionRequestDto extensionRequestDto){


        return ExtensionRequestMapper.toExtensionRequestDto(extensionRequestService.updateStatus(
                subject, extensionRequestId, extensionRequestDto
        ));
    }
}
