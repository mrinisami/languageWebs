package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;
import app.sami.languageWeb.extensionRequest.models.ExtensionRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtensionRequestRepository extends JpaRepository<ExtensionRequest, Long> {
    Optional<ExtensionRequest> findByContractIdAndStatus(Long contractId, ExtensionRequestStatus status);
}
