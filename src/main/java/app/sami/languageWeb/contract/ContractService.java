package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.StorageUriDto;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ContractService {

    private final RequestRepository requestRepository;
    private final Storage storage;

    public StorageUriDto getUploadUri(UUID subject, String fileName){
        String path = String.format("/contracts/%s/%s__%s", subject, UUID.randomUUID(), fileName);
        String url = storage.getUploadPresignedUrl(path);

        return StorageUriDto.builder()
                .url(url)
                .fileName(path)
                .build();
    }
}
