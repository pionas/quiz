package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class ExamAnswerEntity {

    @EmbeddedId
    private ExamAnswerId id;

    private boolean correct;
    private LocalDateTime created;
}
