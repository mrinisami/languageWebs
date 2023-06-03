package app.sami.languageWeb.storedContent;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class StoredContentService {
    private final Storage storage;
    private final StoredContentRepository storedContentRepository;
    private final UserRepository userRepository;

    public String getUploadUri(UUID subject, String fileName) {
        String filePath = String.format("%s/%s", subject, fileName);
        return storage.getUploadPresignedUrl(filePath);
    }

    public StoredContent addText(StoredContent storedContent, UUID subject){
        storedContent.setUserId(subject);
        StoredContent savedStoredContent = storedContentRepository.save(storedContent);

        return savedStoredContent;
    }

    public String getDownloadUri(UUID subject, String fileName){
        StoredContent storedContent = storedContentRepository.findByUserIdAndName(subject, fileName)
                .orElseThrow(NotFoundException::new);
        String filePath = String.format("%s/%s", subject, fileName);

        return storage.getDownloadPresignedUrl(filePath);
    }

    public String getDeleteUri(UUID subject, String fileName){
        StoredContent storedContent = storedContentRepository.findByUserIdAndName(subject, fileName)
                .orElseThrow(NotFoundException::new);
        String filePath = String.format("%s/%s", subject, fileName);

        return storage.getDeletePresignedUrl(filePath);
    }
}
