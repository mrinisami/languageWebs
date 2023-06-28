package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.FilterDto;
import app.sami.languageWeb.request.dtos.PostRequestDto;
import app.sami.languageWeb.request.dtos.RequestDto;
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
        request.setPrice(editRequestDto.getPrice());
        request.setDueDate(editRequestDto.getDueDate() != null ? Instant.ofEpochMilli(editRequestDto.getDueDate()) : null);
        return requestRepository.save(request);
    }
    public String getUploadUri(String path) {
        return storage.getUploadPresignedUrl(path);
    }

    public String getDownloadUri(String path){

        return storage.getDownloadPresignedUrl(path);
    }


}
