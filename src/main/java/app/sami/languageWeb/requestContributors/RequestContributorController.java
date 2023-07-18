package app.sami.languageWeb.requestContributors;

import app.sami.languageWeb.requestContributors.dtos.*;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class RequestContributorController {
    private final RequestContributorRepository requestContributorRepository;

    @GetMapping("/public/request-contributors")
    public RequestsContributorDto getRequestContributors(@RequestParam(required = false) Long requestId,
                                                         @RequestParam(required = false) UUID userId,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "0") int page){
        RequestContributorFilter filter = RequestContributorFilter.builder()
                .requestId(requestId)
                .userId(userId)
                .build();

        return new RequestsContributorDto(requestContributorRepository.findAll(RequestContributorSpecification.createFilter(filter),
                PageRequest.of(page, pageSize)).stream().map((contributor) -> RequestContributorMapper.toDto(
                        contributor
        )).toList());
    }

    @PostMapping("/requests/{requestId}/request-contributors")
    public PostRequestContributor addContribution(@PathVariable Long requestId,
                                                  @RequestJwtSubject UUID subject,
                                                  @RequestBody PostRequestContributorDto requestBody){

        RequestContributor request = requestContributorRepository.save(RequestContributorMapper
                .toRequestContributor(requestBody, requestId, subject));
        return RequestContributorMapper.toPostRequestDto(request);
    }
}
