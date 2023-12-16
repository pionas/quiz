package info.pionas.quiz.domain.exam.api;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class PassExamAnswer {

    private UUID questionId;
    private UUID answerId;

    public boolean isAnswerForQuestion(UUID questionId) {
        return Objects.equals(this.questionId, questionId);
    }
}
