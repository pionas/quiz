package info.pionas.quiz.domain.exam.api;

import info.pionas.quiz.domain.quiz.api.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ExamAnswer {

    private UUID questionId;
    private UUID answerId;
    private Boolean correct;

    public static ExamAnswer of(Question question, PassExamAnswer answerForQuestion) {
        return ExamAnswer.builder()
                .questionId(question.getId())
                .answerId(answerForQuestion.getAnswerId())
                .correct(question.isCorrectAnswer(answerForQuestion))
                .build();
    }
}
