package app.sami.languageWeb.request;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.EditRequestDto;
import app.sami.languageWeb.request.dtos.FilterDto;
import app.sami.languageWeb.request.dtos.RequestLanguageStatsDto;
import app.sami.languageWeb.request.mapper.RequestMapper;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.RequestLanguageStats;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestControllerTests extends IntegrationTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;

    @MockBean
    @Qualifier("storage")
    Storage storage;
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
                .withUser(userTest));
        requestTest2 = requestRepository.save(RequestDtoFactory.generateRequest().withUserId(userTest.getId()));
        requestTest3 = requestRepository.save(RequestDtoFactory.generateRequest().withUserId(userTest.getId()));
    }


    @Test
    void createRequest_Returns200()throws Exception {
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests", userTest.getId());

        mockMvc.perform(post(url, RequestMapper.toPostRequestDto(requestTest), token))
                .andExpect(status().isOk());
    }

    @Test
    void editRequest_Returns200() throws Exception{
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests/%s", userTest.getId(), requestTest.getId());
        EditRequestDto editRequestDto = EditRequestDto.builder()
                .price(30.2)
                .build();

        mockMvc.perform(put(url, editRequestDto, token))
                .andExpect(status().isOk());
    }

    @Test
    void givenDifferingUserIds_ThrowsUserNotAllowed() throws Exception {
        String token = authUser(userTest);
        String url = String.format("/users/%s/requests/%s", UUID.randomUUID(), requestTest.getId());
        EditRequestDto editRequestDto =  EditRequestDto.builder()
                .price(30.2)
                .build();

        mockMvc.perform(put(url, editRequestDto, token))
                .andExpect(status().isForbidden());
    }

    @Test
    void requestStatSummary_Returns200() throws Exception{
        String url = String.format("/public/requests/summary");
        List<RequestLanguageStatsDto> stats = requestRepository.findNbRequestsPerLanguages().stream()
                .map(RequestMapper::toRequestLanguageStats).toList();
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

    }

    @Test
    void getUploadUri_Returns200() throws Exception{
        String token = authUser(userTest);
        String fileName = "bob";
        String url = String.format("/storage/upload-uri", fileName);
        when(storage.getUploadPresignedUrl(contains(fileName))).thenReturn("test.com");

        mockMvc.perform(get(url, token).param("fileName", fileName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("test.com"));
    }

    @Test
    void getFilteredRequests_Returns200() throws Exception{
        String url = "/public/requests";
        List<String> languages = Arrays.asList(requestTest.getSourceLanguage().toString());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("sourceLanguages", languages);
        FilterDto filterDto = FilterDto.builder()
                .priceGt(30.0)
                .priceLt(70.0)
                .build();

        mockMvc.perform(get(url)
                .param("gtPrice", String.valueOf(filterDto.getPriceGt()))
                .param("ltPrice", String.valueOf(filterDto.getPriceLt()))
                        .params(params))
                .andExpect(status().isOk());

    }

    @Test
    void validDeleteRequest_Returns200() throws Exception{
        String url = String.format("/users/%s/requests/%s", requestTest.getUserId(), requestTest.getId());
        String token = authUser(userTest);

        mockMvc.perform(delete(url, token))
                .andExpect(status().isOk());
    }

    @Test
    void wrongUserIdDeleteRequest_Returns404() throws Exception{
        String url = String.format("/users/%s/requests/%s", requestTest.getUserId(), requestTest.getId());
        String token = authUser(userTest1);

        mockMvc.perform(delete(url, token))
                .andExpect(status().isForbidden());
    }
}
