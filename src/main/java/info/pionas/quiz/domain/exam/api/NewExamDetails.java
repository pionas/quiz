package info.pionas.quiz.domain.exam.api;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class NewExamDetails {

    private UUID id;
    private String username;
    private UUID quizId;
    private LocalDateTime created;
}
