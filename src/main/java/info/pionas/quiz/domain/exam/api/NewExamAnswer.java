package info.pionas.quiz.domain.exam.api;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class NewExamAnswer {

    private UUID resultId;
    private UUID questionId;
    private UUID answerId;
    private Boolean correct;
    private LocalDateTime created;

    public static NewExamAnswer correct(UUID resultId, LocalDateTime dateTime, UUID questionId, UUID answerId) {
        return NewExamAnswer.builder()
                .resultId(resultId)
                .questionId(questionId)
                .answerId(answerId)
                .correct(true)
                .created(dateTime)
                .build();
    }

    public static NewExamAnswer wrong(UUID resultId, LocalDateTime dateTime, UUID questionId, UUID answerId) {
        return NewExamAnswer.builder()
                .resultId(resultId)
                .questionId(questionId)
                .answerId(answerId)
                .correct(false)
                .created(dateTime)
                .build();
    }
}
