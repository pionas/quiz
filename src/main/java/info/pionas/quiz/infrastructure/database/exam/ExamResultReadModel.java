package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "quiz_exams")
@Immutable
public class ExamResultReadModel {

    @Id
    private UUID id;
    private String username;
    @OneToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private QuizEntity quiz;

    @ElementCollection
    @CollectionTable(name = "quiz_exam_answers", joinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<ExamAnswerReadModel> answers;

    private LocalDateTime created;
}
