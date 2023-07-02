package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.dtos.*;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Filter;

@Service
@AllArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final Storage storage;

    public Request createRequest(PostRequestDto requestDto, UUID subject){
        Request request = RequestMapper.toRequest(requestDto).withStatus(Status.PENDING)
                .withUserId(subject);
        return requestRepository.save(request);
    }

    @Transactional
    public void deleteRequest(Long requestId, UUID subject){
        Request request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        if (!request.getUserId().equals(subject)){
            throw new UserNotAllowedException();
        }
        requestRepository.delete(request);
    }
    public Request editRequest(EditRequestDto editRequestDto, Long id){
        Request request = requestRepository.findById(id).orElseThrow(NotFoundException::new);
        request.withPrice(editRequestDto.getPrice()).withDueDate(Optional.ofNullable(editRequestDto.getDueDate())
                .map(Instant::ofEpochMilli).orElse(null));
        return requestRepository.save(request);
    }

    public RequestUriDto getUploadUri(UUID subject, String fileName) {
        String path = String.format("/requests/%s/%s_%s", subject, UUID.randomUUID(), fileName);
        String url = storage.getUploadPresignedUrl(path);
        return RequestUriDto.builder()
                .url(url)
                .fileName(path)
                .build();
    }

    public String getDownloadUri(String path){

        return storage.getDownloadPresignedUrl(path);
    }


}
