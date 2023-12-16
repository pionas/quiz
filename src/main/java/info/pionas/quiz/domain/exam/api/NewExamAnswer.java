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

    public static NewExamAnswer of(UUID resultId, ExamAnswer answer, LocalDateTime localDateTime) {
        return NewExamAnswer.builder()
                .resultId(resultId)
                .questionId(answer.getQuestionId())
                .answerId(answer.getAnswerId())
                .correct(answer.getCorrect())
                .created(localDateTime)
                .build();
    }

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
