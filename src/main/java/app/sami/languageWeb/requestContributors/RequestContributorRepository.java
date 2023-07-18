package app.sami.languageWeb.requestContributors;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestContributorRepository extends JpaRepository<RequestContributor, Long>, JpaSpecificationExecutor<RequestContributor> {
    List<RequestContributor> findByRequestId(Long requestId);
    List<RequestContributor> findByUserId(UUID userId);
}
