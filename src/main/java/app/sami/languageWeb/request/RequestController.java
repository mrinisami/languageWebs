package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.dtos.RequestsDto;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return RequestMapper.toRequestDto(requestService.createRequest(requestDto));
    }

    @PutMapping("/users/{userId}/requests/{requestId}")
    public RequestDto editRequest(@PathVariable UUID userId,
                                  @PathVariable Long requestId,
                                  @RequestJwtSubject UUID subject,
                                  @RequestBody EditRequestDto editRequestDto)
    {
        if (subject != userId){
            throw new UserNotAllowedException();
        }
        return RequestMapper.toRequestDto(requestService.editRequest(editRequestDto, requestId));
    }
}
