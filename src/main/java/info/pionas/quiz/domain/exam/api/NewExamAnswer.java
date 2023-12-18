package info.pionas.quiz.domain.exam.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class NewExamAnswer {

    private UUID resultId;
    private UUID questionId;
    private UUID answerId;
    private Boolean correct;
    private LocalDateTime created;
}
