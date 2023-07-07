package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.ContractDto;
import app.sami.languageWeb.contract.dtos.StorageUriDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.storage.Storage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        if (request.isRequester(subject)){
            throw new UserNotAllowedException();
        }
        requestRepository.save(request.withStatus(app.sami.languageWeb.request.models.Status.ACCEPTED));
        return contractRepository.save(Contract.builder()
                .contractedUserId(subject)
                .contractStatus(ContractStatus.PENDING)
                .requestId(requestId)
                .build());
    }

    public Contract editContract(UUID subject, Long contractId, ContractDto contractDto){
        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);

        return contractRepository.save(contract.withFilePath(contractDto.getFilePath()));
    }

    public void pingContractOwnerPastDueDate(){
        //TODO
    }
}
