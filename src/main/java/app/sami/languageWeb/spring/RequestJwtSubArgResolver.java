package app.sami.languageWeb.spring;

import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

import static org.checkerframework.checker.nullness.Opt.orElseThrow;

@Component
@AllArgsConstructor
public class RequestJwtSubArgResolver implements HandlerMethodArgumentResolver {

    @Autowired
    RequestJwtClaimsExtractor requestJwtClaimsExtractor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJwtSubject.class);
    }

    @Override
    public UUID resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return requestJwtClaimsExtractor.extract().flatMap(RequestJwtClaims::getSubject)
                .orElseThrow(UserNotAllowedException::new);
    }
}
