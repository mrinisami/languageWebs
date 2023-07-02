package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.StorageUriDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.Status;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
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
import org.simpleframework.xml.ElementArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ContractServiceTests extends IntegrationTests {
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
                .filePath("test")
                .status(Status.PENDING)
                .build());


    }

    @Test
    void matchingUploadUri_ReturnsTrue(){
        String path = requestTest.getName();
        when(storage.getUploadPresignedUrl(contains(path))).thenReturn("test.com");
        StorageUriDto result = contractService.getUploadUri(requestTest.getUserId(), requestTest.getName());

        assertThat(result.getUrl()).isEqualTo("test.com");
    }

    @Test
    void matchingCreateContact_ReturnsTrue(){
        String path = "translated";
        Contract result = contractService.createContract(userTest2.getId(), requestTest.getId(), path).withCreatedAt(null)
                .withModifiedAt(null);
        Contract expected = Contract.builder()
                        .contractedUserId(userTest2.getId())
                                .filePath(path)
                        .status(Status.PENDING)
                                        .requestId(requestTest.getId())
                .id(result.getId())
                                                .build();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenSameUserRequestAndContractCreateContact_ThrowsUserNotAllowed(){
        String path = "translated";
        Contract result = contractService.createContract(userTest2.getId(), requestTest.getId(), path).withCreatedAt(null)
                .withModifiedAt(null);
        Contract expected = Contract.builder()
                .contractedUserId(userTest2.getId())
                .filePath(path)
                .status(Status.PENDING)
                .requestId(requestTest.getId())
                .id(result.getId())
                .build();

        assertThrows(UserNotAllowedException.class, () -> contractService.createContract(userTest.getId(), requestTest.getId(),
                path));
    }

    @Test
    void matchingFileChangePath_ReturnsTrue(){
        String path = "newPath";
        Contract result = contractService.changeContractFile(userTest2.getId(), contractTest.getId(), path);
        Contract expected = contractTest.withFilePath(path);

        assertThat(result.getFilePath().equals(expected.getFilePath()));
    }

    @Test
    void matchingNewDueDate_ReturnsTrue(){
        Instant expected = Instant.now();
        Contract result = contractService.pushBackDueDate(contractTest.getId(), userTest.getId(), expected);

        assertThat(result.getRequest().getDueDate()).isEqualTo(expected);
    }

    @Test
    void givenSameUserForRequestAndContractPushBack_ThrowsUnauthorizedUser(){
        assertThrows(UserNotAllowedException.class, () -> contractService.pushBackDueDate(contractTest.getId(),
                userTest2.getId(), Instant.now()));
    }
}
