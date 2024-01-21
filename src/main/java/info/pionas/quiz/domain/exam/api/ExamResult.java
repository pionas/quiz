package info.pionas.quiz.domain.exam.api;

import info.pionas.quiz.domain.quiz.api.Quiz;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ExamResult {

    private UUID id;
    private String username;
    private Quiz quiz;
    private List<ExamAnswer> answers;
    private LocalDateTime created;

    public long getCorrectAnswerCount() {
        return answers.stream()
                .filter(ExamAnswer::getCorrect)
                .count();
    }

    public double getCorrectAnswerPercentage() {
        long totalQuestions = quiz.getQuestions().size();
        if (totalQuestions == 0) {
            return 0.0; // Avoid division by zero
        }

        long correctAnswers = getCorrectAnswerCount();
        return ((double) correctAnswers / totalQuestions) * 100;
    }
}
