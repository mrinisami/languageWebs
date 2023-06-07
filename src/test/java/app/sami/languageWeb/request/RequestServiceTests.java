package app.sami.languageWeb.request;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.PostRequestDto;
import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.dtos.RequestUriDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.storage.MinioStorage;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.testUtils.factories.RequestDtoFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

public class RequestServiceTests extends IntegrationTests {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestService requestService;
    @Autowired
    UserRepository userRepository;
    @MockBean
    @Qualifier("storage")
    Storage storage;
    User userTest;
    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());
    }

    @Test
    void matchCreateRequest_ReturnsTrue(){
        PostRequestDto requestDto = RequestDtoFactory.generatePostRequestDto()
                .withTranslatedLanguage(Language.ABKHAZ)
                .withSourceLanguage(Language.SAMOAN);
        Request result = requestService.createRequest(requestDto, userTest.getId());
        result.setId(null);
        Request expected = Request.builder()
                        .price(requestDto.getPrice())
                .status(Status.PENDING)
                .translatedLanguage(Language.ABKHAZ)
                .sourceLanguage(Language.SAMOAN)
                .userId(userTest.getId())
                .name(requestDto.getName())
                .build();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void matchingEditRequest_ReturnsTrue(){
        EditRequestDto editRequestDto =  EditRequestDto.builder()
                .price(30.2)
                .build();
        Request request = requestRepository.save(Request.builder()
                .status(Status.PENDING)
                .price(30.0)
                .sourceLanguage(Language.AFAR)
                .translatedLanguage(Language.ABKHAZ)
                        .userId(userTest.getId())
                .build());
        Request result = requestService.editRequest(editRequestDto, request.getId());
        Request expected = request.withPrice(editRequestDto.getPrice());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void matchingUploadUrl_ReturnsTrue(){
        String name = UUID.randomUUID().toString();
        String filePath = String.format("%s/%s", userTest.getId(), name);
        when(storage.getUploadPresignedUrl(contains(name))).thenReturn("test.com");
        RequestUriDto result = requestService.getUploadUri(userTest.getId(), name);

        assertThat(result.getUrl()).isEqualTo("test.com");
    }

}
