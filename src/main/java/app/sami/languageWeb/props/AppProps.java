package app.sami.languageWeb.props;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@AllArgsConstructor
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProps {
    @NotNull
    public final JksProps jks;
    @URL
    public final String baseURL;
    @URL
    public final String issuer;
}
