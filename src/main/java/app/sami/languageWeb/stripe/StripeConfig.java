package app.sami.languageWeb.stripe;

import app.sami.languageWeb.props.AppProps;
import app.sami.languageWeb.props.StripeProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Autowired
    private AppProps appProps;

    @Bean
    public StripeApiConfig stripeApiConfig(){
        StripeProps stripe = appProps.stripe;

        return StripeApiConfig.builder().apiKey(stripe.apiKey).cancelUri(stripe.cancelUri)
                .successUri(stripe.successUri).build();
    }
}
