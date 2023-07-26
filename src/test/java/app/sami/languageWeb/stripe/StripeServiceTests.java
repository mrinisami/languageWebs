package app.sami.languageWeb.stripe;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.PaymentAlreadyMadeException;
import app.sami.languageWeb.error.exceptions.StripeCheckoutSessionException;
import app.sami.languageWeb.payment.Payment;
import app.sami.languageWeb.payment.PaymentRepository;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.stripe.dtos.CreateSessionDto;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.RequestFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StripeServiceTests extends IntegrationTests {
    @Autowired
    UserRepository userRepository;

    private StripeService stripeService;
    @Mock
    StripeApi stripeApi;
    @Autowired
    StripeAccountRepository stripeAccountRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
            StripeApiConfig stripeApiConfig;
    Contract contractTest;
    private User userTest;
    private User userTest2;
    Request requestTest;
    private StripeAccount stripeAccountTest;

    @BeforeEach
    void setup(){
        stripeService = new StripeService(stripeAccountRepository, stripeApi, userRepository, contractRepository,
                paymentRepository, stripeApiConfig);
        stripeAccountRepository.deleteAll();
        userTest = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        requestTest = requestRepository.save(RequestFactory.generateRequest().withUserId(userTest.getId()));
        contractTest = contractRepository.save(Contract.builder()
                .requestId(requestTest.getId())
                .contractedUserId(userTest2.getId())
                .filePath("test")
                .contractStatus(ContractStatus.PENDING)
                .build());

    }

    @Test
        void givenStripeCallFails_throwsException() throws StripeException {
        stripeAccountTest = stripeAccountRepository.save(StripeAccount.builder()
                .userId(userTest2.getId())
                .accountId("123")
                .build());
        CreateSessionDto createSessionDto = CreateSessionDto.builder()
                .price(30.0)
                .nbWords((long) 20)
                .cancelUrl("test")
                .successUrl("test")
                .build();

        when(stripeApi.createSession(any(SessionCreateParams.class), any(RequestOptions.class)))
                .thenThrow(Mockito.mock(StripeException.class));

        assertThrows(StripeCheckoutSessionException.class,
                () -> stripeService.createSessionPayment(createSessionDto, contractTest.getId(), userTest.getId()));
    }

    @Test
    void givenStripeCallSuccessCreateSession_ReturnsTrue() throws StripeException {
        stripeAccountTest = stripeAccountRepository.save(StripeAccount.builder()
                .userId(userTest2.getId())
                .accountId("123")
                .build());
        CreateSessionDto createSessionDto = CreateSessionDto.builder()
                .price(30.0)
                .nbWords((long) 20)
                .cancelUrl("test")
                .successUrl("test")
                .build();
        when(stripeApi.createSession(any(SessionCreateParams.class), any(RequestOptions.class))).thenReturn("test");

        assertThat(stripeService.createSessionPayment(createSessionDto, contractTest.getId(), userTest.getId())).isEqualTo("test");

    }

    @Test
    void givenStripeCallSuccessAndNoContractCreateSession_ThrowsNotFoundException() throws StripeException {
        CreateSessionDto createSessionDto = CreateSessionDto.builder()
                .price(30.0)
                .nbWords((long) 20)
                .cancelUrl("test")
                .successUrl("test")
                .build();
        when(stripeApi.createSession(any(SessionCreateParams.class), any(RequestOptions.class))).thenReturn("test");

        assertThrows(NotFoundException.class, () ->
                stripeService.createSessionPayment(createSessionDto, (long) 66, userTest.getId()));

    }

    @Test
    void givenAlreadyMadePaymentCreateSesssion_ThrowsPaymentAlreadyMadeException() throws StripeException{
        CreateSessionDto createSessionDto = CreateSessionDto.builder()
                .price(30.0)
                .nbWords((long) 20)
                .cancelUrl("test")
                .successUrl("test")
                .build();
        paymentRepository.save(Payment.builder()
                .payment(30.0)
                .contractId(contractTest.getId())
                .build());
        when(stripeApi.createSession(any(SessionCreateParams.class), any(RequestOptions.class))).thenReturn("test");

        assertThrows(PaymentAlreadyMadeException.class, () ->
                stripeService.createSessionPayment(createSessionDto, contractTest.getId(), userTest.getId()));
    }

    @Test
    void NotEmptyCreateStripeAccount_ReturnsTrue() throws StripeException{
        StripeAccount expected = StripeAccount.builder()
                .userId(userTest.getId())
                .accountId("1233")
                .build();
        when(stripeApi.createAccount(userTest)).thenReturn(expected);
        StripeAccount result = stripeService.createAccount(userTest.getId());


        assertThat(stripeAccountRepository.findById(result.getId())).isNotEmpty();
    }

}
