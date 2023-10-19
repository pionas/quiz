package info.pionas.quiz.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.util.Locale;

@TestConfiguration
class RestITConfiguration {

    private static final String ROOT_URI = "http://localhost:50000/api/v1";

    @Bean
    TestRestTemplate apiRestClient() {
        final var testRestTemplate = new TestRestTemplate(createRestTemplateBuilder());
        testRestTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
        return testRestTemplate;
    }

    private RestTemplateBuilder createRestTemplateBuilder() {
        return new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, Locale.US.toString())
                .rootUri(ROOT_URI);
    }
}
