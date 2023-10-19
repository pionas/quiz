package info.pionas.quiz.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration(proxyBeanMethods = false)
class ApiWebFluxConfiguration implements WebFluxConfigurer {

    private static final String API_CONTEXT = "/api/v1";

    @Override
    public void configurePathMatching(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_CONTEXT, HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
