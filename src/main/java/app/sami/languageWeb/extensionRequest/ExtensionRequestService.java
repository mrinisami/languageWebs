package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;
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

    public ExtensionRequest updateStatus(UUID subject, Long extensionId, ExtensionRequestDto extensionRequestDto){
        ExtensionRequest extensionRequest = extensionRequestRepository.findById(extensionId).orElseThrow(
                NotFoundException::new
        );
        extensionRequest.updateStatus(subject, extensionRequestDto.getStatus());
        if (extensionRequest.isAccepted()){
            updateRequestDueDate(subject, extensionRequestDto.getDate(), extensionRequest.getContract().getRequestId());
        }
        return extensionRequestRepository.save(extensionRequest);
    }

    private void updateRequestDueDate(UUID subject, Instant date, Long requestId){
        Request request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        request.updateDueDate(subject, date);
        requestRepository.save(request);
    }
}
