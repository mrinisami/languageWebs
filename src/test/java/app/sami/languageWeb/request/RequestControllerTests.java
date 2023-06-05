package app.sami.languageWeb.request;

import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.RequestsDto;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.storedContent.StoredContent;
import app.sami.languageWeb.storedContent.StoredContentRepository;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.testUtils.factories.RequestDtoFactory;
import app.sami.languageWeb.testUtils.factories.StoredContentFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestControllerTests extends IntegrationTests {

    private User userTest;
    private StoredContent storedContentTest;
    private StoredContent storedContentTest2;
    private StoredContent storedContentTest3;
    private Request requestTest = RequestDtoFactory.generateRequest();
    private Request requestTest2 = RequestDtoFactory.generateRequest();
    private Request requestTest3 = RequestDtoFactory.generateRequest();
    @Autowired
    UserRepository userRepository;
    @Autowired
    StoredContentRepository storedContentRepository;
    @Autowired
    RequestRepository requestRepository;

    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());
        storedContentTest = storedContentRepository.save(StoredContentFactory.storedContentGenerator()
                .withUserId(userTest.getId()));
        storedContentTest2 = storedContentRepository.save(StoredContentFactory.storedContentGenerator()
                .withUserId(userTest.getId()));
        storedContentTest3 = storedContentRepository.save(StoredContentFactory.storedContentGenerator()
                .withUserId(userTest.getId()));
        requestTest = requestRepository.save(requestTest.withContentId(storedContentTest.getId()));
        requestTest2 = requestRepository.save(requestTest.withContentId(storedContentTest.getId()));

        requestTest3 = requestRepository.save(requestTest.withContentId(storedContentTest2.getId()))
                ;
    }

    @Test
    void getRequestList_Returns200() throws Exception{
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests", userTest.getId());

        mockMvc.perform(get(url, token))
                .andExpect(status().isOk());
    }

    @Test
    void createRequest_Returns200()throws Exception {
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests", userTest.getId());

        mockMvc.perform(post(url, RequestMapper.toRequestDto(requestTest), token))
                .andExpect(status().isOk());
    }

    @Test
    void editRequest_Returns200() throws Exception{
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests/%s", userTest.getId(), requestTest.getId());
        EditRequestDto editRequestDto = new EditRequestDto(Randomize.grade());

        mockMvc.perform(put(url, editRequestDto, token))
                .andExpect(status().isOk());
    }

    @Test
    void givenDifferingUserIds_ThrowsUserNotAllowed() throws Exception {
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests/%s", UUID.randomUUID(), requestTest.getId());
        EditRequestDto editRequestDto = new EditRequestDto(Randomize.grade());

        mockMvc.perform(put(url, editRequestDto, token))
                .andExpect(status().isForbidden());
    }

}
