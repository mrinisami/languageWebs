package app.sami.languageWeb.stripe;

import app.sami.languageWeb.user.models.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Component;

@Component
public class StripeApi {

    private final StripeApiConfig stripeApiConfig;

    public StripeApi(StripeApiConfig stripeApiConfig){
        this.stripeApiConfig = stripeApiConfig;
        Stripe.apiKey = stripeApiConfig.apiKey;
    }
    public StripeAccount createAccount(User user) throws StripeException {
            Account account = Account.create(AccountCreateParams.builder()
                    .setEmail(user.getEmail())
                    .setType(AccountCreateParams.Type.EXPRESS)
                    .build());
            StripeAccount stripeAccount = StripeAccount.builder()
                    .accountId(account.getId())
                    .userId(user.getId())
                    .build();
            return stripeAccount;

    }

    public String createSession(SessionCreateParams params, RequestOptions requestOptions) throws StripeException {

        return Session.create(params, requestOptions).getUrl();
    }

    public Session getSession(String sessionId) throws StripeException{
        return Session.retrieve(sessionId);
    }

}
