package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.*;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import app.sami.languageWeb.request.models.RequestSpecification;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.simpleframework.xml.Path;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final Storage storage;

    @PostMapping("/users/{userId}/requests")
    public PostRequestDto addRequest(@PathVariable UUID userId,
                                 @RequestJwtSubject UUID subject,
                                 @RequestBody PostRequestDto requestDto)
    {
        return RequestMapper.toPostRequestDto(requestService.createRequest(requestDto, subject));
    }

    @DeleteMapping("/users/{userId}/requests/{requestId}")
    public void deleteRequest(@PathVariable UUID userId,
                              @PathVariable Long requestId,
                              @RequestJwtSubject UUID subject){
        requestService.deleteRequest(requestId, subject);
    }

    @PutMapping("/users/{userId}/requests/{requestId}")
    public RequestDto editRequest(@PathVariable UUID userId,
                                  @PathVariable Long requestId,
                                  @RequestJwtSubject UUID subject,
                                  @RequestBody EditRequestDto editRequestDto)
    {
        if (!subject.equals(userId)){
            throw new UserNotAllowedException();
        }
        return RequestMapper.toRequestDto(requestService.editRequest(editRequestDto, requestId));
    }

    @GetMapping("/public/requests/summary")
     public RequestLanguagesStatsDto getRequestLanguageStats(){
        List<RequestLanguageStatsDto> requestLanguageStatsDtos = requestRepository.findNbRequestsPerLanguages()
                .stream().map(RequestMapper::toRequestLanguageStats).toList();

        return new RequestLanguagesStatsDto(requestLanguageStatsDtos);
    }

    @GetMapping("/requests/{requestId}")
    public RequestDto getRequest(@RequestJwtSubject UUID subject,
                                 @PathVariable Long requestId){
        return RequestMapper.toRequestDto(requestRepository.findById(requestId).orElseThrow(NotFoundException::new));
    }
    @GetMapping("/storage/upload-uri")
    public RequestUriDto getUploadUri(@RequestParam String fileName,
                                      @RequestJwtSubject UUID subject){
        return requestService.getUploadUri(subject, fileName);
    }

    @GetMapping("/storage/download-uri")
    public RequestUriDto getDownloadUri(@RequestJwtSubject UUID subject,
                                        @RequestParam String path){
        return RequestUriDto.builder()
                .url(storage.getDownloadPresignedUrl(path))
                .fileName(path)
                .build();
    }
    @GetMapping("/public/requests")
    public RequestsDto getFilteredRequests(@RequestParam(required = false) Double min,
                                           @RequestParam(required = false) Double max,
                                           @RequestParam(required = false) List<Language> sourceLanguages,
                                           @RequestParam(required = false) List<Language> translatedLanguages,
                                           @RequestParam(required = false) UUID userId,
                                           @RequestParam(required = false) List<Status> status,
                                           @RequestParam(required = false) Long dueDate,
                                           @RequestParam(defaultValue = "modifiedAt") String sortingVariable,
                                           @RequestParam(defaultValue = "DESC") String sortOrder,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(defaultValue = "0") int page){
        FilterDto filter = FilterDto.builder()
                .priceGt(min)
                .priceLt(max)
                .sourceLanguages(sourceLanguages)
                .translatedLanguages(translatedLanguages)
                .userId(userId)
                .statuses(status)
                .dueDate(dueDate != null ? Instant.ofEpochMilli(dueDate) : null)
                .build();
        return new RequestsDto(requestRepository.findAll(RequestSpecification.createFilter(filter),
                                                 PageRequest.of(page, pageSize,
                                                         Sort.by(Sort.Direction.fromString(sortOrder), sortingVariable)))
                .stream()
                .map(RequestMapper::toRequestDto).toList());
    }
}
