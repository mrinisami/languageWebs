package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.StorageUriDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.Status;
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

    public Contract createContract(UUID subject, Long requestId, String path){
        Request request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        if (request.getUserId().equals(subject)){
            throw new UserNotAllowedException();
        }
        requestRepository.save(request.withStatus(app.sami.languageWeb.request.models.Status.ACCEPTED));
        return contractRepository.save(Contract.builder()
                .contractedUserId(subject)
                .filePath(path)
                .status(Status.PENDING)
                .requestId(requestId)
                .build());
    }

    public Contract changeContractFile(UUID subject, Long contractId, String path){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
        if (subject.equals(contract.getRequest().getUserId())){
            throw new UserNotAllowedException();
        }
        return contractRepository.save(contract.withFilePath(path));
    }

    public Contract pushBackDueDate(Long contractId, UUID subject, Instant dueDate){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
        if (!subject.equals(contract.getRequest().getUserId())){
            throw new UserNotAllowedException();
        }
        requestRepository.save(contract.getRequest().withDueDate(dueDate));

        return contractRepository.findById(contractId).orElseThrow(NotFoundException::new);
    }

    public void pingContractOwnerPastDueDate(){
        //TODO
    }
}
