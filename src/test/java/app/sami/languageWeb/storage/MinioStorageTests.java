package app.sami.languageWeb.storage;

import app.sami.languageWeb.testUtils.IntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class MinioStorageTests extends IntegrationTests {

    @Autowired
    private MinioStorage minioStorage;

    @Test
    void getPresignedUrl_ReturnsString() throws Exception{
        assertThat(minioStorage.getUploadPresignedUrl("test")).isNotBlank();
    }
}
