package app.sami.languageWeb;

import app.sami.languageWeb.props.AppProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class LanguageWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageWebApplication.class, args);
	}

}
