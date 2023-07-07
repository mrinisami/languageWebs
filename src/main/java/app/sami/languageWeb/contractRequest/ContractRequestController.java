package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contractRequest.dtos.ContractRequestDto;
import app.sami.languageWeb.contractRequest.dtos.StatusDto;
import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.simpleframework.xml.Path;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ContractRequestController {
    private final ContractRequestService contractRequestService;

    @PostMapping("/requests/{requestId}/contract-request")
    public ContractRequestDto createContractRequest(@PathVariable Long requestId,
                                                    @RequestJwtSubject UUID subject){
        return ContractRequestMapper.toContractRequestDto(contractRequestService.createContractRequest(subject, requestId));
    }

    @PutMapping("/contract-request/{contractRequestId}")
    public ContractRequestDto renderDecision(@PathVariable Long contractRequestId,
                                             @RequestBody StatusDto status,
                                             @RequestJwtSubject UUID subject){
        return ContractRequestMapper.toContractRequestDto(contractRequestService
                .renderContractRequestDecision(status.getStatus(), contractRequestId, subject));
    }
}
