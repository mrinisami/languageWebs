package app.sami.languageWeb.requestContributor;

import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.requestContributors.dtos.PostRequestContributorDto;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.RequestDtoFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestContributorTests extends IntegrationTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    private User userTest;
    private User userTest1;
    private Request requestTest;
    private Request requestTest2;
    private Request requestTest3;

    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());
        userTest1 = userRepository.save(UserFactory.userGenerator());
        requestTest = requestRepository.save(RequestDtoFactory.generateRequest().withUserId(userTest.getId())
                .withUser(userTest)
        );
    }

    @Test
    void addContributor_Returns200() throws Exception{
        String url = String.format("/requests/%s/request-contributors", requestTest.getId());
        String token = authUser(userTest1);

        mockMvc.perform(post(url, new PostRequestContributorDto(5.03), token))
                .andExpect(status().isOk());
    }

    @Test
    void getContributor_Returns200() throws Exception{
        String url = String.format("/public/request-contributors");

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }
}
