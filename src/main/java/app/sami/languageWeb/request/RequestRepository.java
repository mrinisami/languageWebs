package app.sami.languageWeb.request;

import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.RequestInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "SELECT r.id, r.source_language, r.translated_language, r.status, r.content_id, r.modified_at, r.price, s.name " +
            "FROM request AS r " +
            "JOIN stored_content AS s " +
            "ON r.content_id = s.id " +
            "WHERE s.user_id = :userId " +
            "ORDER BY r.modified_at",
    nativeQuery = true)
    List<RequestInfo> findByUserId(@Param("userId") UUID userId, PageRequest of);

}
