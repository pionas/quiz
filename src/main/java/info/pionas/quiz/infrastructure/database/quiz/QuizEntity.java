package info.pionas.quiz.infrastructure.database.quiz;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "quizes")
public class QuizEntity {

    @Id
    private UUID id;
    private String title;
    private String description;
    private String username;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;
}
