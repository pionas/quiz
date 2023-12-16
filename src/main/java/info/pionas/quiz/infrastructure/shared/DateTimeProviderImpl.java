package info.pionas.quiz.infrastructure.shared;

import info.pionas.quiz.domain.shared.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class DateTimeProviderImpl implements DateTimeProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
