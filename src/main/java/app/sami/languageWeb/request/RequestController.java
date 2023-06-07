package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.*;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import app.sami.languageWeb.utils.RequestSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static app.sami.languageWeb.request.mapper.RequestMapper.toRequestLanguageStats;

@RestController
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    @GetMapping("/users/{userId}/requests")
    public RequestsDto getRequests(@PathVariable UUID userId,
                                   @RequestJwtSubject UUID subject,
                                   @RequestParam(defaultValue = "2") int pageSize,
                                   @RequestParam(defaultValue = "0") int page){
        List<RequestDto> requests = requestRepository.findByUserId(userId, PageRequest.of(page, pageSize)).stream()
                .map(RequestMapper::toRequestDto)
                .toList();

        return new RequestsDto(requests);
    }

    @PostMapping("/users/{userId}/requests")
    public RequestDto addRequest(@PathVariable UUID userId,
                                 @RequestJwtSubject UUID subject,
                                 @RequestBody RequestDto requestDto)
    {
        return RequestMapper.toRequestDto(requestService.createRequest(requestDto, subject));
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

    @GetMapping("/requests/{fileName}/upload-uri")
    public RequestUriDto getUploadUri(@PathVariable String fileName,
                                      @RequestJwtSubject UUID subject){
        return RequestUriDto.builder()
                .url(requestService.getUploadUri(subject, fileName))
                .fileName(fileName)
                .build();
    }
    @GetMapping("/requests/{fileName}/download-uri")
    public RequestUriDto getDownloadUri(@PathVariable String fileName,
                                      @RequestJwtSubject UUID subject){
        return RequestUriDto.builder()
                .url(requestService.getDownloadUri(subject, fileName))
                .fileName(fileName)
                .build();
    }

    @GetMapping("/public/requests")
    public RequestsDto getFilteredRequests(@RequestParam(required = false) Double gtPrice,
                                           @RequestParam(required = false) Double ltPrice,
                                           @RequestParam(required = false) List<Language> sourceLanguages,
                                           @RequestParam(required = false) List<Language> translatedLanguages,
                                           @RequestParam(required = false) UUID userId){
        FilterDto filter = FilterDto.builder()
                .priceGt(gtPrice)
                .priceLt(ltPrice)
                .sourceLanguages(sourceLanguages)
                .translatedLanguages(translatedLanguages)
                .userId(userId)
                .build();
        return new RequestsDto(requestRepository.findAll(RequestSpecification.createFilter(filter)).stream()
                .map(RequestMapper::toRequestDto).toList());
    }
}
