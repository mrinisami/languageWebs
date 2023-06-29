package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByRequestId(Long id);
    List<Contract> findByContractedUserId(UUID userId);
}
