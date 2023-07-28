package app.sami.languageWeb.stripe;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.ContractStatus;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StripeControllerTests extends IntegrationTests {
    @MockBean
    private StripeService stripeService;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;

    private String successUrl = "test.com/success";
    private String cancelUrl = "test.com/cancel";
    private String redirectUrl = "frontend";
    private Long contractId = (long) 30;
    private String sessionId = "123";
    private User userTest;
    private User userTest2;
    private Request requestTest;
    private Contract contractTest;

    @BeforeEach
    void setup(){
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
    void givenValidRequestPostCheckout_Returns3xx() throws Exception{
        String token = authUser(userTest);
        String url = "/contracts/3/checkout-session";
        String expectedUrl = "test";
        when(stripeService.createSessionPayment(any(CreateSessionDto.class), any(Long.class), any(UUID.class)))
                .thenReturn(expectedUrl);
        mockMvc.perform(post(url, new CreateSessionDto(), token)
                ).andExpect(status().is3xxRedirection());

    }

    @Test
    void givenCheckoutSuccss_Returns3xx() throws Exception{
        String url = String.format("/checkout/success?session_id=%s&&redirect_uri=%s&contract_id=%s", sessionId,
                redirectUrl, contractTest.getId());


        mockMvc.perform(get(url)).andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, redirectUrl));

    }

    @Test
    void givenCheckoutSuccessNoContract_Throws404() throws Exception{
        String url = String.format("/checkout/success?session_id=%s&&redirect_uri=%s&contract_id=%s", sessionId,
                redirectUrl, 2222);


        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }
}
