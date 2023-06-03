package app.sami.languageWeb.storedContent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoredContentRepository extends JpaRepository<StoredContent, Long> {
    Optional<StoredContent> findByUserIdAndName(UUID userId, String name);
}
