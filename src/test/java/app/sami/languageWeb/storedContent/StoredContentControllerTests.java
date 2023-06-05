package app.sami.languageWeb.storedContent;


import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.StoredContentFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.storedContent.dtos.StoredContentDto;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoredContentControllerTests extends IntegrationTests {

    @MockBean
    @Qualifier("storage")
    private Storage storage;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoredContentRepository storedContentRepository;
    private User userTest;
    private StoredContent storedContent;

    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());

    }

    @Test
    void getURL_Returns200AndTrue() throws Exception{
        String url = String.format("/store-content/%s/upload-uri", "bobthebuilder");
        String token = authUser(userTest);
        when(storage.getUploadPresignedUrl(contains(userTest.getId().toString()))).thenReturn("test.com");
        mockMvc.perform(get(url, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("test.com"));
    }

    @Test
    void getDownloadURL_Returns200AndTrue() throws Exception{
        String fileName = "bobthebuilder";
        storedContent = StoredContentFactory.storedContentGenerator()
                .withUserId(userTest.getId())
                .withName(fileName);
        storedContentRepository.save(storedContent);
        String url = String.format("/store-content/%s/download-uri", fileName);
        String token = authUser(userTest);

        when(storage.getDownloadPresignedUrl(contains(userTest.getId().toString()))).thenReturn("test.com");
        mockMvc.perform(get(url, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("test.com"));
    }

    @Test
    void givenEmptyRepoDownloadURL_Returns404() throws Exception {
        String fileName = "bobthebuilder";
        String url = String.format("/store-content/%s/download-uri", fileName);
        String token = authUser(userTest);
        when(storage.getDownloadPresignedUrl(contains(userTest.getId().toString()))).thenReturn("test.com");
        mockMvc.perform(get(url, token))
                .andExpect(status().isNotFound());
    }

    @Test
    void addText_Returns200() throws Exception{
        String url = "/stored-contents";
        String token = authUser(userTest);
        StoredContentDto storedContentDto = StoredContentFactory.storedContentDtoGenerator()
                        .withName("test");

        mockMvc.perform(post(url, storedContentDto, token))
                .andExpect(status().isOk());
    }

    @Test
    void matchDeleteUri_Returns200AndTrue() throws Exception{
        String fileName = "bobthebuilder";
        storedContent = StoredContentFactory.storedContentGenerator()
                .withUserId(userTest.getId())
                .withName(fileName);
        storedContentRepository.save(storedContent);
        String url = String.format("/store-content/%s/delete-uri", fileName);
        String token = authUser(userTest);

        when(storage.getDeletePresignedUrl(contains(userTest.getId().toString()))).thenReturn("test.com");
        mockMvc.perform(get(url, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("test.com"));
    }

}
