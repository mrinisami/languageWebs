package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contractRequest.dtos.ContractRequestDto;
import app.sami.languageWeb.contractRequest.dtos.ContractRequestsDto;
import app.sami.languageWeb.contractRequest.dtos.StatusDto;
import app.sami.languageWeb.contractRequest.models.ContractRequest;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.RequestRepository;
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

    @PostMapping("/requests/{requestId}/contract-requests")
    public ContractRequestDto createContractRequest(@PathVariable Long requestId,
                                                    @RequestJwtSubject UUID subject){
        return ContractRequestMapper.toContractRequestDto(contractRequestService.createContractRequest(subject, requestId));
    }

    @PutMapping("/contract-requests/{contractRequestId}")
    public ContractRequestDto renderDecision(@PathVariable Long contractRequestId,
                                             @RequestBody StatusDto status,
                                             @RequestJwtSubject UUID subject){
        return ContractRequestMapper.toContractRequestDto(contractRequestService
                .renderContractRequestDecision(status.getStatus(), contractRequestId, subject));
    }

    @GetMapping("/contract-requests")
    public ContractRequestsDto getContractRequests(@RequestParam Long requestId,
                                                   @RequestJwtSubject UUID subject){

        List<ContractRequest> contractRequests = contractRequestRepository.findByRequestId(requestId).stream()
                .map((request) -> {
                    if (!request.isRequester(subject)){throw new UserNotAllowedException("Only the creator of the" +
                            " requests can access their contract requests.");
                    }
                    return request;
                }).toList();

        return new ContractRequestsDto(contractRequests.stream().map(ContractRequestMapper::toContractRequestDto).toList());
    }

    @GetMapping("/users/{userId}/contract-requests")
    public ContractRequestDto getContractRequestByUserId(@RequestParam Long requestId,
                                                         @RequestJwtSubject UUID subject,
                                                         @PathVariable UUID userId){
        return ContractRequestMapper.toContractRequestDto(contractRequestRepository.findByRequestIdAndUserId(
                requestId, subject
        ).orElseThrow(NotFoundException::new));
    }
}
