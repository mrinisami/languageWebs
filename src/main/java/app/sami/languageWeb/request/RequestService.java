package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.FilterDto;
import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final Storage storage;

    public Request createRequest(RequestDto requestDto, UUID subject){
        Request request = RequestMapper.toRequest(requestDto).withStatus(Status.PENDING)
                .withUserId(subject);
        return requestRepository.save(request);
    }

    public Request editRequest(EditRequestDto editRequestDto, Long id){
        Request request = requestRepository.findById(id).orElseThrow(NotFoundException::new);
        request.setPrice(editRequestDto.getPrice());

        return requestRepository.save(request);
    }
    public String getUploadUri(UUID subject, String path) {
        return storage.getUploadPresignedUrl(path);
    }
    public String getDownloadUri(UUID subject, String path){
        return storage.getDownloadPresignedUrl(path);
    }

}
