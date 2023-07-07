package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.contractRequest.dtos.StatusDto;
import app.sami.languageWeb.contractRequest.models.ContractRequest;
import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import app.sami.languageWeb.request.RequestRepository;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.storage.Storage;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.RequestFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContractRequestControllerTests extends IntegrationTests {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    ContractRequestRepository contractRequestRepository;
    @MockBean
    @Qualifier("storage")
    Storage storage;
    Request requestTest;
    User userTest;
    User userTest2;
    Contract contractTest;
    ContractRequest contractRequest;

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
        contractRequest = contractRequestRepository.save(ContractRequest.builder()
                .userId(userTest2.getId())
                .status(ContractRequestStatus.PENDING)
                .requestId(requestTest.getId())
                .build());
    }

    @Test
    void createContractRequest_Returns200() throws Exception{
        String token = authUser(userTest2);
        String url = String.format("/requests/%s/contract-request", requestTest.getId());

        mockMvc.perform(post(url).header("authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void renderContractDecision_Returns200AndCorrectStatus() throws Exception{
        String token = authUser(userTest);
        String url = String.format("/contract-request/%s", contractRequest.getId());

        mockMvc.perform(put(url, new StatusDto(ContractRequestStatus.ACCEPTED), token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ContractRequestStatus.ACCEPTED.toString()));
    }

    @Test
    void renderContractDecision_Returns404() throws Exception{
        String token = authUser(userTest2);
        String url = String.format("/contract-request/%s", contractRequest.getId());

        mockMvc.perform(put(url, new StatusDto(ContractRequestStatus.ACCEPTED), token))
                .andExpect(status().isForbidden());

    }
}
