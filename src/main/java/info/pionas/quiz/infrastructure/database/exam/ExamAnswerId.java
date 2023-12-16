package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
class ExamAnswerId {

    private UUID resultId;
    private UUID questionId;
    private UUID answerId;
}
