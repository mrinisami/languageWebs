package app.sami.languageWeb.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ArgResolverConfig implements WebMvcConfigurer {
    @Autowired
    private RequestJwtSubArgResolver requestJwtSubArgResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(requestJwtSubArgResolver);
    }

}
