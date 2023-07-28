package app.sami.languageWeb.stripe;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.payment.Payment;
import app.sami.languageWeb.payment.PaymentRepository;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import app.sami.languageWeb.stripe.dtos.CreateSessionDto;
import lombok.AllArgsConstructor;
import org.simpleframework.xml.Path;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class StripeController {

    private final StripeService stripeService;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;

    @PostMapping("/contracts/{contractId}/checkout-session")
    public RedirectView openCheckoutSession(@RequestJwtSubject UUID subject,
                                            @PathVariable Long contractId,
                                            @RequestBody CreateSessionDto createSessionDto){
        return new RedirectView(stripeService.createSessionPayment(createSessionDto, contractId, subject));
    }

    @GetMapping("/checkout/success")
    public RedirectView getCheckoutSuccess(@RequestParam("session_id") String sessionId,
                                           @RequestParam("redirect_uri") String redirectUri,
                                           @RequestParam("contract_id") Long contractId){

        Contract contract = contractRepository.findById(contractId).orElseThrow(NotFoundException::new);

        paymentRepository.save(Payment.builder()
                .payment(contract.getTotalPrice())
                .contractId(contractId)
                .build());

        return new RedirectView(redirectUri);
    }

    @GetMapping("/checkout/cancel")
    public RedirectView getCheckoutCancel(@RequestParam("redirect_uri") String redirectUri){

        return new RedirectView(redirectUri);
    }
}
