package app.sami.languageWeb.request;

import app.sami.languageWeb.request.dtos.FilterDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.testUtils.factories.RequestDtoFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import app.sami.languageWeb.utils.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatList;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class RequestRepositoryTests extends IntegrationTests {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;
    private User userTest;
    private User userTest2;
    private Request requestTest;
    private Request requestTest2;
    private Request requestTest3;

    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        requestTest = requestRepository.save(RequestDtoFactory.generateRequest().withPrice(28.0).withUserId(userTest.getId()));
        requestTest2 = requestRepository.save(RequestDtoFactory.generateRequest().withPrice(55.0)
                .withUserId(userTest.getId()));
        requestTest3 = requestRepository.save(RequestDtoFactory.generateRequest().withPrice(88.0)
                .withUserId(userTest2.getId()));
    }

    @Test
    void matchingRequestResultfromCustomSpecs_ReturnsTrue(){
        FilterDto filterDto = FilterDto.builder()
                .userId(userTest2.getId())
                .sourceLanguages(Arrays.asList(requestTest3.getSourceLanguage()))
                .translatedLanguages(Arrays.asList(requestTest3.getTranslatedLanguage()))
                .build();
        List<Request> result = requestRepository.findAll(RequestSpecification.createFilter(filterDto));
        List<Request> expected = new ArrayList<>();
        expected.add(requestTest3);
        assertThatList(result).hasSameElementsAs(expected);
    }

    @Test
    void nonMatchingRequestResultFromCustomSpecs_ReturnsTrue(){
        FilterDto filterDto = FilterDto.builder()
                .userId(userTest2.getId())
                .sourceLanguages(Arrays.asList(Randomize.language()))
                .build();
        List<Request> result = requestRepository.findAll(RequestSpecification.createFilter(filterDto));
        List<Request> expected = new ArrayList<>();
        expected.add(requestTest3);
        assertThatList(result).doesNotContainAnyElementsOf(expected);
    }

    @Test
    void givenEmptyRepo_ReturnsEmptyList(){
        FilterDto filterDto = FilterDto.builder()
                .userId(UUID.randomUUID())
                .build();
        List<Request> result = requestRepository.findAll(RequestSpecification.createFilter(filterDto));

        assertThatList(result).isEmpty();
    }
}
