package app.sami.languageWeb.props;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
public class StripeProps {

    @NotBlank
    public final String apiKey;

    @URL
    public final String successUri;

    @URL
    public final String cancelUri;
}
