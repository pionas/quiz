package info.pionas.quiz.infrastructure.shared;

import info.pionas.quiz.domain.shared.UuidGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class UuidGeneratorImpl implements UuidGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
