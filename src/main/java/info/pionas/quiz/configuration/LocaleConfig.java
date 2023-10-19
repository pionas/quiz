package info.pionas.quiz.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.server.i18n.LocaleContextResolver;

@Configuration(proxyBeanMethods = false)
class InternationalizationConfiguration extends DelegatingWebFluxConfiguration {

    @Override
    protected LocaleContextResolver createLocaleContextResolver() {
        return new RequestParamLocaleContextResolver();
    }
}
