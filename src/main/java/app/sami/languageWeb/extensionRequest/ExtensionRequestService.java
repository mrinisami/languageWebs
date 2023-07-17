package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestStatusDto;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequestStatus;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExtensionRequestService {
    private final ExtensionRequestRepository extensionRequestRepository;
    private final RequestRepository requestRepository;
    private final ContractRepository contractRepository;

    public ExtensionRequest updateStatus(UUID subject, Long extensionId, ExtensionRequestStatusDto extensionRequestDto){
        ExtensionRequest extensionRequest = extensionRequestRepository.findById(extensionId).orElseThrow(
                NotFoundException::new
        );
        extensionRequest.updateStatus(subject, extensionRequestDto.getStatus());
        if (extensionRequestDto.getStatus() == ExtensionRequestStatus.ACCEPTED){
            Request request = extensionRequest.getContract().getRequest();
            requestRepository.save(request.withDueDate(extensionRequest.getProposedDate()));
        }
        return extensionRequestRepository.save(extensionRequest);
    }

}
