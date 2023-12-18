package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "quiz_exam_answers")
public class ExamAnswerEntity {

    @EmbeddedId
    private ExamAnswerId id;

    private boolean correct;
    private LocalDateTime created;
}
