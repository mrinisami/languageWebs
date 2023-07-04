package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.ContractStatusDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.Status;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContractControllerTests extends IntegrationTests {
    @Autowired
    ContractService contractService;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    @MockBean
    @Qualifier("storage")
    Storage storage;
    Request requestTest;
    User userTest;
    User userTest2;
    Contract contractTest;

    @BeforeEach
    void setup(){
        userTest = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        requestTest = requestRepository.save(RequestFactory.generateRequest().withUserId(userTest.getId()));
        contractTest = contractRepository.save(Contract.builder()
                .requestId(requestTest.getId())
                .contractedUserId(userTest2.getId())
                .status(Status.PENDING)
                        .contractorStatus(Status.PENDING)
                        .contractedStatus(Status.CANCELLED)
                .build());
    }

    @Test
    void getUploadUri_Returns200() throws Exception{
        String url = "/contracts/storage/upload-uri";
        String token = authUser(userTest);

        mockMvc.perform(post(url, "test", token))
                .andExpect(status().isOk());
    }

    @Test
    void addDocument_Returns200() throws Exception{
        String url = String.format("/contracts/%s/file", contractTest.getId());
        String token = authUser(userTest2);

        mockMvc.perform(put(url, "translated", token))
                .andExpect(status().isOk());
    }

    @Test
    void givenWrongUserAddDocument_Returns404() throws Exception{
        String url = String.format("/contracts/%s/file", contractTest.getId());
        String token = authUser(userTest);

        mockMvc.perform(put(url, "translated", token))
                .andExpect(status().isForbidden());
    }

    @Test
    void giveExtension_Returns200() throws Exception{
        String url = String.format("/contracts/%s/extension", contractTest.getId());
        String token = authUser(userTest);

        mockMvc.perform(put(url, (long) 303030, token))
                .andExpect(status().isOk());
    }

    @Test
    void givenWrongUserGiveExtension_Returns404() throws Exception{
        String url = String.format("/contracts/%s/extension", contractTest.getId());
        String token = authUser(userTest2);

        mockMvc.perform(put(url, (long) 303030, token))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateStatus_Returns200() throws Exception{
        String url = String.format("/contracts/%s/status", contractTest.getId());
        String token = authUser(userTest);

        mockMvc.perform(put(url, new ContractStatusDto(Status.CANCELLED), token))
                .andExpect(status().isOk());
    }

    @Test
    void updateStatusCompletedWithoutFile_Returns403() throws Exception{
        String url = String.format("/contracts/%s/status", contractTest.getId());
        String token = authUser(userTest);

        mockMvc.perform(put(url, new ContractStatusDto(Status.COMPLETED), token))
                .andExpect(status().isBadRequest());
    }
}
