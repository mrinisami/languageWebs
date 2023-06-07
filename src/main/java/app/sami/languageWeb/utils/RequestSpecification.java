package app.sami.languageWeb.utils;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.FilterDto;
import app.sami.languageWeb.request.models.Request;
import jakarta.persistence.criteria.Root;
import org.hibernate.type.EntityType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RequestSpecification {

    public static Specification<Request> createFilter(FilterDto filterDto){
        return sourceLanguageIn(filterDto.getSourceLanguages())
                .and(translatedLanguageIn(filterDto.getTranslatedLanguages()))
                .and(priceGt(filterDto.getPriceGt()))
                .and(priceLt(filterDto.getPriceLt()))
                .and(userOrComm(filterDto.getUserId()));
    }
    static Specification<Request> sourceLanguageIn(List<Language> languages){
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(languages)){
                return criteriaBuilder.conjunction();
            }
            return root.get("sourceLanguage").in(languages);
        };
    }

    static Specification<Request> translatedLanguageIn(List<Language> languages){
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(languages)){
                return criteriaBuilder.conjunction();
            }
            return root.get("translatedLanguage").in(languages);
        };
    }
    static Specification<Request> priceGt(Double price){
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(price)){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

    static Specification<Request> priceLt(Double price){
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(price)){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
        };
    }

    static Specification<Request> userOrComm(UUID userId){
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(userId)){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("userId"), userId);
        };
    }
}
