package app.sami.languageWeb.stripe;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class StripeApiConfig {
    public final String apiKey;
    public final String successUri;
    public final String cancelUri;
}
