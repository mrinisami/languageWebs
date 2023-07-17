package app.sami.languageWeb.ExtensionRequest;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.extensionRequest.ExtensionRequestRepository;
import app.sami.languageWeb.extensionRequest.ExtensionRequestService;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequestStatus;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.RequestFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExtensionRequestControllerTests extends IntegrationTests {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    ExtensionRequestService extensionRequestService;
    @Autowired
    ExtensionRequestRepository extensionRequestRepository;
    Request requestTest;
    User userTest;
    User userTest2;
    User userTest3;
    Contract contractTest;
    ExtensionRequest extensionRequest;
    Instant newDate = Instant.now().plus(2, ChronoUnit.DAYS);
    @BeforeEach
    void setup(){
        userRepository.deleteAll();
        requestRepository.deleteAll();
        userTest = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        userTest3 = userRepository.save(UserFactory.userGenerator());
        requestTest = requestRepository.save(RequestFactory.generateRequest().withUserId(userTest.getId()));
        contractTest = contractRepository.save(Contract.builder()
                .requestId(requestTest.getId())
                .contractedUserId(userTest2.getId())
                .contractStatus(ContractStatus.PENDING)
                .build());
        extensionRequest = extensionRequestRepository.save(ExtensionRequest.builder()
                .userId(userTest2.getId())
                .status(ExtensionRequestStatus.PENDING)
                .contractId(contractTest.getId())
                .proposedDate(newDate)
                .build());
    }

    @Test
    void getExtension_Returns200() throws Exception{
        String url = String.format("/extension-requests", contractTest.getId());
        String token = authUser(userTest);

        mockMvc.perform(get(url, token)
                .param("contractId", contractTest.getId().toString()))
                .andExpect(status().isOk());

    }

    @Test
    void givenWrongUserGetExtension_Returns403() throws Exception{
        String url = String.format("/extension-requests", contractTest.getId());
        String token = authUser(userTest2);

        mockMvc.perform(get(url, token)
                        .param("contractId", contractTest.getId().toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void createExtension_Returns200() throws Exception{
        String url = String.format("/extension-request");
        ExtensionRequestDto extensionRequestDto = ExtensionRequestDto.builder()
                .status(ExtensionRequestStatus.PENDING)
                .date(Instant.now().toEpochMilli())
                .contractId(contractTest.getId())
                .build();
        String token = authUser(userTest2);

        mockMvc.perform(post(url, extensionRequestDto, token))
                .andExpect(status().isOk());
    }

    @Test
    void updateStatus_Returns200() throws Exception{
        String url = String.format("/contracts/%s/extensionRequests/%s", contractTest.getId(), extensionRequest.getId());
        String token = authUser(userTest2);

        mockMvc.perform(put(url, ExtensionRequestDto.builder()
                .status(ExtensionRequestStatus.CANCELLED)
                .build(), token))
                .andExpect(status().isOk());
    }
}
