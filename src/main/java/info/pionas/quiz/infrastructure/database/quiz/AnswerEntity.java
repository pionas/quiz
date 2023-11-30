package info.pionas.quiz.infrastructure.database.quiz;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "quiz_answers")
public class AnswerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;
    private Boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

}