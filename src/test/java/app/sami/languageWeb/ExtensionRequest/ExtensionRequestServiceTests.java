package app.sami.languageWeb.ExtensionRequest;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.contractRequest.ContractRequestRepository;
import app.sami.languageWeb.contractRequest.ContractRequestService;
import app.sami.languageWeb.contractRequest.models.ContractRequest;
import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.extensionRequest.ExtensionRequestRepository;
import app.sami.languageWeb.extensionRequest.ExtensionRequestService;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestDto;
import app.sami.languageWeb.extensionRequest.dtos.ExtensionRequestStatusDto;
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
import java.time.temporal.TemporalUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExtensionRequestServiceTests extends IntegrationTests {

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
    void matchingUpdateStatusDenied_ReturnsTrue(){
        ExtensionRequestStatus result = extensionRequestService.updateStatus(userTest.getId(), extensionRequest.getId(),
                ExtensionRequestStatusDto.builder()
                        .status(ExtensionRequestStatus.DENIED)
                        .build()).getStatus();
        assertThat(result).isEqualTo(ExtensionRequestStatus.DENIED);
    }

    @Test
    void matchingRequestDateUpdateStatus_ReturnsTrue(){
        extensionRequestService.updateStatus(userTest.getId(), extensionRequest.getId(),
                ExtensionRequestStatusDto.builder()
                        .status(ExtensionRequestStatus.ACCEPTED)
                        .build());
        Instant result = requestRepository.findById(requestTest.getId()).orElseThrow(NotFoundException::new).getDueDate();

        assertThat(result).isEqualTo(newDate);
    }
}
