package app.sami.languageWeb.contractRequest;

import app.sami.languageWeb.contractRequest.models.ContractRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRequestRepository extends JpaRepository<ContractRequest, Long> {
    List<ContractRequest> findByRequestId(Long requestId);
    Optional<ContractRequest> findByRequestIdAndUserId(Long requestId, UUID userId);
}
