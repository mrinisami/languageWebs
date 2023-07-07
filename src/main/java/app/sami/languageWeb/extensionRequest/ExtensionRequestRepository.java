package app.sami.languageWeb.extensionRequest;

import app.sami.languageWeb.extensionRequest.models.ExtensionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionRequestRepository extends JpaRepository<ExtensionRequest, Long> {
}
