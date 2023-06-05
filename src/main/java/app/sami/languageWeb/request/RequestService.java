package app.sami.languageWeb.request;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public Request createRequest(RequestDto requestDto){
        Request request = RequestMapper.toRequest(requestDto);
        request.setStatus(Status.PENDING);
        return requestRepository.save(request);
    }

    public Request editRequest(EditRequestDto editRequestDto, Long id){
        Request request = requestRepository.findById(id).orElseThrow(NotFoundException::new);
        request.setPrice(editRequestDto.getPrice());
        request.setModifiedAt(Instant.now());

        return requestRepository.save(request);
    }

}
