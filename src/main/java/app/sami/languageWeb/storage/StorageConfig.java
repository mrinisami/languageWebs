package app.sami.languageWeb.storage;

import app.sami.languageWeb.props.AppProps;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private AppProps appProps;
    @Autowired
    private MinioClient minioClient;
    @Bean
    public Storage storage(){
        return minioStorage();
    }
    @Bean
    public MinioStorage minioStorage(){
        return new MinioStorage(appProps.minio.bucketName, minioClient);
    }
}
