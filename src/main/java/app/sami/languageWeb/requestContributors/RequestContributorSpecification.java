package app.sami.languageWeb.requestContributors;

import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.requestContributors.dtos.RequestContributorFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

public class RequestContributorSpecification {

    public static Specification<RequestContributor> createFilter(RequestContributorFilter filter){
        return requestId(filter.getRequestId())
                .and(userId(filter.getUserId()));
    }
    private static Specification<RequestContributor> requestId(Long requestId){
        return optSpec(requestId, (r, q, cb) -> cb.equal(r.get("requestId"), requestId));
    }
    private static Specification<RequestContributor> userId(UUID userId){
        return optSpec(userId, (r, q, cb) ->
             cb.equal(r.get("userId"), userId)
        );
    }
    private static <T>Specification<RequestContributor> optSpec(T obj, Specification<RequestContributor> spec){
        return Objects.isNull(obj) ? ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()) : spec;
    }
}
