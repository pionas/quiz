package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Setter
class ExamAnswerId {

    @Column(name = "result_id")
    private UUID resultId;
    private UUID questionId;
    private UUID answerId;
}
