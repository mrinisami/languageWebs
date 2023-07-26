package app.sami.languageWeb.stripe;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.error.exceptions.*;
import app.sami.languageWeb.payment.Payment;
import app.sami.languageWeb.payment.PaymentRepository;
import app.sami.languageWeb.stripe.dtos.CreateSessionDto;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import com.stripe.model.Account;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StripeService {
    private final StripeAccountRepository stripeRepository;
    private final StripeApi stripeApi;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;
    private final StripeApiConfig stripeConfig;
    public StripeAccount createAccount(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        try {
            StripeAccount account = stripeApi.createAccount(user);
            return stripeRepository.save(account);
        }
        catch (Exception e){
            throw new StripeAccountException(e.getMessage());
        }
    }

    public String createSessionPayment(CreateSessionDto createSessionDto, Long contractId, UUID subject){
        Contract contract = contractRepository.findById(contractId).orElseThrow(() ->
                new NotFoundException(("No contract has been found")));

        Optional<Payment> payment = paymentRepository.findByContractId(contractId);

        if (payment.isPresent()){
            throw new PaymentAlreadyMadeException();
        }
        if (!contract.isContractor(subject)){
            throw new UserNotAllowedException("Only the contracted user can access payment modal");
        }

        StripeAccount stripeAccount = stripeRepository.findByUserId(contract.getContractedUserId())
                .orElseThrow(() -> new NotFoundException("No stripe account has been found."));

        try {
            return stripeApi.createSession(createParams(createSessionDto, contractId),
                    createRequestOptions(stripeAccount.getAccountId()));
        }
        catch (Exception e){
            throw new StripeCheckoutSessionException(e.getMessage());
        }
    }

    private String successUrl(String successUrl, Long contractId, String redirectUrl){
        return String.format("/%s?session_id={CHECKOUT_SESSION_ID}&contract_id=%s&redirect_uri=%s", stripeConfig.successUri,
                contractId, redirectUrl);
    }

    private String cancelUrl(String cancelUrl, String redirectUrl){
        return String.format("/%s?redirect_url=%s", stripeConfig.cancelUri, redirectUrl);
    }

    private SessionCreateParams createParams(CreateSessionDto createSessionDto, Long contractId){
        String url = successUrl(createSessionDto.getSuccessUrl(), contractId, createSessionDto.getSuccessUrl());
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(createSessionDto.getPrice().toString())
                        .setQuantity(createSessionDto.getNbWords())
                        .build())
                .setSuccessUrl(url)
                .setCancelUrl(cancelUrl(createSessionDto.getCancelUrl(), createSessionDto.getCancelUrl()))
                .build();
    }

    private RequestOptions createRequestOptions(String accountId){
        return RequestOptions.builder()
                .setStripeAccount(accountId)
                .build();
    }
}
