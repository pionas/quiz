package info.pionas.quiz.domain.exam.api;

import info.pionas.quiz.domain.quiz.api.Quiz;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ExamResult {

    private UUID id;
    private Quiz quiz;
    private List<ExamAnswer> answers;

    public long getCorrectAnswer() {
        return answers.stream()
                .filter(ExamAnswer::getCorrect)
                .count();
    }
}
