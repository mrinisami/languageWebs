package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.StorageUriDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.Status;
import app.sami.languageWeb.error.exceptions.ContractTranslatedFileAbsent;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContractService {

    private final RequestRepository requestRepository;
    private final Storage storage;
    private final ContractRepository contractRepository;

    public StorageUriDto getUploadUri(UUID subject, String fileName){
        String path = String.format("/contracts/%s/%s__%s", subject, UUID.randomUUID(), fileName);
        String url = storage.getUploadPresignedUrl(path);

        return StorageUriDto.builder()
                .url(url)
                .fileName(path)
                .build();
    }

    public Contract createContract(UUID subject, Long requestId){
        Request request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        if (request.isUser(subject)){
            throw new UserNotAllowedException();
        }
        requestRepository.save(request.withStatus(app.sami.languageWeb.request.models.Status.ACCEPTED));
        return contractRepository.save(Contract.builder()
                .contractedUserId(subject)
                .status(Status.PENDING)
                .requestId(requestId)
                .build());
    }

    public Contract updateContractParticipantStatus(UUID subject, Long contractId, Status status){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
        if (status.equals(Status.COMPLETED) && !contract.isFileAdded()){
            throw new ContractTranslatedFileAbsent();
        }

        contract = contractRepository.save(contract.updateStatus(subject, status));

        if (contract.isContractedStatus(Status.CANCELLED) && contract.isContractorStatus(Status.CANCELLED)){
            return cancelContract(contractId);
        }
        return contract;
    }
    private Contract cancelContract(Long contractId){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
        //TODO enforce cancelling fees
        return contractRepository.save(contract.withStatus(Status.CANCELLED));
    }
    public Contract changeContractFile(UUID subject, Long contractId, String path){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
        if (contract.isContractor(subject)){
            throw new UserNotAllowedException();
        }
        return contractRepository.save(contract.withFilePath(path));
    }

    public Contract pushBackDueDate(Long contractId, UUID subject, Instant dueDate){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
        if (!contract.isContractor(subject)){
            throw new UserNotAllowedException();
        }
        requestRepository.save(contract.getRequest().withDueDate(dueDate));

        return contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
    }

    public void pingContractOwnerPastDueDate(){
        //TODO
    }
}
