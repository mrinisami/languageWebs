package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contract.ContractRepository;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contractRequest.models.ContractRequest;
import app.sami.languageWeb.contractRequest.models.ContractRequestStatus;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContractRequestServiceTests extends IntegrationTests {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    ContractRequestRepository contractRequestRepository;
    @Autowired
    ContractRequestService contractRequestService;
    Request requestTest;
    User userTest;
    User userTest2;
    User userTest3;
    Contract contractTest;
    ContractRequest contractRequest;
    ContractRequest contractRequest2;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
        requestRepository.deleteAll();
        userTest = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        userTest3 = userRepository.save(UserFactory.userGenerator());
        requestTest = requestRepository.save(RequestFactory.generateRequest().withUserId(userTest.getId()));
        contractRequest = contractRequestRepository.save(ContractRequest.builder()
                .userId(userTest2.getId())
                .status(ContractRequestStatus.PENDING)
                .requestId(requestTest.getId())
                .build());
        contractRequest2 = contractRequestRepository.save(ContractRequest.builder()
                .userId(userTest3.getId())
                .status(ContractRequestStatus.PENDING)
                .requestId(requestTest.getId())
                .build());
    }

    @Test
    void renderContractDecisionChangesStatusForAllRequests_ReturnsTrue(){
        contractRequestService.renderContractRequestDecision(ContractRequestStatus.ACCEPTED, contractRequest.getId(),
                userTest.getId());
        ContractRequestStatus result = contractRequestRepository.findById(contractRequest2.getId()).orElseThrow(NotFoundException::new)
                .getStatus();
        assertThat(result).isEqualTo(ContractRequestStatus.DENIED);
    }
    @Test
    void renderContractRequestDecision_CreatesContract(){
        contractRequestService.renderContractRequestDecision(ContractRequestStatus.ACCEPTED, contractRequest.getId(),
                userTest.getId());


        Contract result = contractRepository.findByRequestId(requestTest.getId()).orElseThrow(NotFoundException::new);

        assertThat(result).isNotNull();
    }

    @Test
    void wrongUserRendersContractDecision_ThrowsUserNotAllowed(){
        assertThrows(UserNotAllowedException.class, () -> contractRequestService.renderContractRequestDecision(
                ContractRequestStatus.ACCEPTED, contractRequest.getId(), userTest2.getId()
        ));
    }
}
