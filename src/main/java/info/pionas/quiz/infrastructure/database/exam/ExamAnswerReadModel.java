package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class ExamAnswerReadModel {

    private UUID answerId;
    private UUID questionId;
    private boolean correct;
    private LocalDateTime created;

}
