package app.sami.languageWeb.storedContent;

import app.sami.languageWeb.error.exceptions.MinioPutUriException;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.StoredContentFactory;
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

public class StoredContentServiceTests extends IntegrationTests {

    @Autowired
    StoredContentRepository storedContentRepository;

    @MockBean
    @Qualifier("storage")
    Storage storage;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoredContentService storedContentService;
    StoredContent storedContentTest = StoredContentFactory.storedContentGenerator().withName("test");
    StoredContent storedContentTest2;
    User userTest = UserFactory.userGenerator();

    @BeforeEach
    void setup(){
        userRepository.save(userTest);
        storedContentTest2 = storedContentRepository.save(StoredContentFactory.storedContentGenerator().withName("bob")
                .withUserId(userTest.getId()));
    }
    @Test
    void matchAddText_ReturnsTrue(){
        StoredContent result = storedContentService.addText(storedContentTest, userTest.getId());
        result.setId(null);
        assertThat(result).isEqualTo(storedContentTest.withUserId(userTest.getId()));
    }

    @Test
    void matchGetDownloadUrl_ReturnsTrue(){
        when(storage.getDownloadPresignedUrl(contains("bob"))).thenReturn("test.com");
        String result = storedContentService.getDownloadUri(userTest.getId(), "bob");

        assertThat(result).isEqualTo("test.com");
    }

    @Test
    void givenEmptyRepoDownloadUrl_ThrowsNotFound(){
        assertThrows(NotFoundException.class, () -> storedContentService.getDownloadUri(UUID.randomUUID(), "boba"));
    }

}
