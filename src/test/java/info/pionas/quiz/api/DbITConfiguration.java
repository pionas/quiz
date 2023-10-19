package info.pionas.quiz.api;

import jakarta.persistence.EntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
class DbITConfiguration {

    @Bean
    TestDbUtil testDbUtil(EntityManager entityManager) {
        return new TestDbUtil("", entityManager);
    }
}
