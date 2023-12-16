package info.pionas.quiz.infrastructure.database.exam;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class ExamEntity {

    @Id
    private UUID id;
    private String username;
    private UUID quizId;
    private LocalDateTime created;
}
