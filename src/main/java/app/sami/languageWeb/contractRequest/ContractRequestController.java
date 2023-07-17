package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contractRequest.dtos.ContractRequestDto;
import app.sami.languageWeb.contractRequest.dtos.ContractRequestsDto;
import app.sami.languageWeb.contractRequest.dtos.StatusDto;
import app.sami.languageWeb.contractRequest.models.ContractRequest;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ContractRequestController {
    private final ContractRequestService contractRequestService;
    private final ContractRequestRepository contractRequestRepository;
    private final RequestRepository requestRepository;

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

    @GetMapping("/contract-requests")
    public ContractRequestsDto getContractRequests(@RequestParam Long requestId,
                                                   @RequestJwtSubject UUID subject){
        Request request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        if (!request.isRequester(subject)){
            throw new UserNotAllowedException();
        }
        List<ContractRequest> contractRequests = contractRequestRepository.findByRequestId(requestId);

        return new ContractRequestsDto(contractRequests.stream().map(ContractRequestMapper::toContractRequestDto).toList());
    }

    @GetMapping("/contract-request")
    public ContractRequestDto getContractRequest(@RequestParam Long requestId,
                                                 @RequestJwtSubject UUID subject,
                                                 @RequestParam UUID userId){
        return ContractRequestMapper.toContractRequestDto(contractRequestRepository.findByRequestIdAndUserId(
                requestId, subject
        ).orElseThrow(NotFoundException::new));
    }
}
