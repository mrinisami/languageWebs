package app.sami.languageWeb.request;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.storedContent.StoredContent;
import app.sami.languageWeb.storedContent.StoredContentRepository;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.testUtils.factories.RequestDtoFactory;
import app.sami.languageWeb.testUtils.factories.StoredContentFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestServiceTests extends IntegrationTests {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestService requestService;
    @Autowired
    StoredContentRepository storedContentRepository;
    @Autowired
    UserRepository userRepository;
    User userTest;
    StoredContent storedContent;
    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());
        storedContent = storedContentRepository.save(StoredContentFactory.storedContentGenerator()
                .withUserId(userTest.getId()));
    }

    @Test
    void matchCreateRequest_ReturnsTrue(){
        RequestDto requestDto = RequestDtoFactory.generateRequestDto()
                .withContentId(storedContent.getId())
                .withSourceLanguage(storedContent.getSourceLanguage())
                .withTranslatedLanguage(Language.ABKHAZ);
        Request result = requestService.createRequest(requestDto);
        result.setId(null);
        Request expected = Request.builder()
                        .price(requestDto.getPrice())
                .contentId(storedContent.getId())
                .status(Status.PENDING)
                .sourceLanguage(storedContent.getSourceLanguage())
                .translatedLanguage(Language.ABKHAZ)
                .build();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void matchingEditRequest_ReturnsTrue(){
        EditRequestDto editRequestDto = new EditRequestDto(Randomize.grade());
        Request request = requestRepository.save(Request.builder()
                .status(Status.PENDING)
                .price(30.0)
                .contentId(storedContent.getId())
                .sourceLanguage(Language.AFAR)
                .translatedLanguage(Language.ABKHAZ)
                .build());
        Request result = requestService.editRequest(editRequestDto, storedContent.getId());
        Request expected = request.withPrice(editRequestDto.getPrice());

        assertThat(result).isEqualTo(expected);
    }
}
