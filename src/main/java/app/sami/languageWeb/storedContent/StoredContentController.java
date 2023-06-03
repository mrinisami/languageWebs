package app.sami.languageWeb.storedContent;

import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import app.sami.languageWeb.storedContent.dtos.StoredContentDto;
import app.sami.languageWeb.storedContent.dtos.StoredContentURIDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class StoredContentController {

    private StoredContentService storedContentService;
    @GetMapping("/store-content/{fileName}/upload-uri")
    public StoredContentURIDto getUploadUri(@RequestJwtSubject UUID subject,
                                            @PathVariable String fileName) {
        return new StoredContentURIDto(storedContentService.getUploadUri(subject, fileName));
    }

    @GetMapping("/store-content/{fileName}/download-uri")
    public StoredContentURIDto getDownloadUri(@RequestJwtSubject UUID subject,
                                            @PathVariable String fileName) {
        return new StoredContentURIDto(storedContentService.getDownloadUri(subject, fileName));
    }

    @PostMapping("/stored-contents")
    public StoredContentDto addText(@RequestJwtSubject UUID subject, @RequestBody StoredContentDto textInfo){
        return toStoredContentDto(storedContentService.addText(toStoredContent(textInfo), subject));
    }

    private StoredContent toStoredContent(StoredContentDto textInfo){
        return StoredContent.builder()
                .name(textInfo.getName())
                .sourceLanguage(textInfo.getSourceLanguage())
                .translatedLanguage(textInfo.getTranslatedLanguage())
                .build();
    }

    private StoredContentDto toStoredContentDto(StoredContent storedContent){
        return StoredContentDto.builder()
                .sourceLanguage(storedContent.getSourceLanguage())
                .translatedLanguage(storedContent.getTranslatedLanguage())
                .name(storedContent.getName())
                .build();
    }
}
