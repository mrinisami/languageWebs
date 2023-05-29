package app.sami.languageWeb.config;

import app.sami.languageWeb.props.AppProps;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MinioConfig {

    @Autowired
    private AppProps appProps;
    @Bean
    @Primary
    public MinioClient minioClient(){
        return new MinioClient.
                Builder()
                .credentials(appProps.minio.username, appProps.minio.password)
                .endpoint(appProps.minio.url)
                .build();
    }
}
