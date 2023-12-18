package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "quiz_exams")
public class ExamEntity {

    @Id
    private UUID id;
    private String username;
    @Column(name = "quiz_id")
    private UUID quizId;
    private LocalDateTime created;
}
