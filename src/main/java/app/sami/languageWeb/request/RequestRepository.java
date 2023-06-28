package app.sami.languageWeb.request;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.models.RequestLanguageStats;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.RequestInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

    @Query(value= "" +
            "SELECT source_language as sourceLanguage, translated_language as translatedLanguage, " +
            "COUNT(source_language) as nbRequests " +
            "FROM request " +
            "GROUP BY (source_language, translated_language) " +
            "ORDER BY COUNT(source_language) DESC",
    nativeQuery = true)
    List<RequestLanguageStats> findNbRequestsPerLanguages();

}
