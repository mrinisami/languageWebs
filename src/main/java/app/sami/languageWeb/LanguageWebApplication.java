package app.sami.languageWeb;

import app.sami.languageWeb.props.AppProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
@EnableJpaAuditing
public class LanguageWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageWebApplication.class, args);
	}

}
