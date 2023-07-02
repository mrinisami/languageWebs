package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.ContractFilterDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.Status;
import app.sami.languageWeb.request.models.Request;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ContractSpecification {

    public static Specification<Contract> createFilter(ContractFilterDto contractFilterDto){
        return statuses(contractFilterDto.getStatuses())
                .and(requestUserId(contractFilterDto.getRequestUserId()))
                .and(contractedUserId(contractFilterDto.getContractedUserId()));
    }
    static Specification<Contract> statuses(List<Status> statuses){
        return optSpec(statuses, (root, query, criteriaBuilder) -> root.get("status").in(statuses));
    }

    static Specification<Contract> requestUserId(UUID userId){
        return optSpec(userId, (r, q, cb) -> {
            Join<Request, Contract> request = r.join("request");
            return cb.equal(request.get("userId"), userId);
        });
    }

    static Specification<Contract> contractedUserId(UUID userId){
        return optSpec(userId, (r, q, cb) -> cb.equal(r.get("contractedUserId"), userId));
    }
    private static <T> Specification<Contract> optSpec(T obj, Specification<Contract> spec){
        return Objects.isNull(obj) ? ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()) : spec;
    }
}
