package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contract.ContractService;
import app.sami.languageWeb.contractRequest.models.ContractRequest;
import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContractRequestService {

    private final ContractRequestRepository contractRequestRepository;
    private final ContractService contractService;
    public ContractRequest createContractRequest(UUID subject, Long requestId){
        return contractRequestRepository.save(ContractRequest.builder()
                .requestId(requestId)
                .status(ContractRequestStatus.PENDING)
                .userId(subject)
                .build());
    }

    public ContractRequest renderContractRequestDecision(ContractRequestStatus status, Long contractRequestId, UUID subject){
        ContractRequest contractRequest = contractRequestRepository.findById(contractRequestId).orElseThrow(NotFoundException::new);
        if (!contractRequest.isRequester(subject)){
            throw new UserNotAllowedException();
        }
        if (status == ContractRequestStatus.ACCEPTED){
            contractService.createContract(contractRequest.getUserId(), contractRequest.getRequestId());
            List<ContractRequest> contractsToUpdate = contractRequestRepository.findByRequestId(contractRequest.getRequestId());
            for (ContractRequest contract: contractsToUpdate){
                if (contract.getUserId().equals(contractRequest.getUserId())){
                    continue;
                }
                contractRequestRepository.save(contract.withStatus(ContractRequestStatus.DENIED));
            }
        }
        return contractRequestRepository.save(contractRequest.withStatus(status));
    }
}
