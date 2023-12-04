package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
class QuizValidator {

    private final List<QuizValidationRule> quizValidationRules;

    public void validate(NewQuiz newQuiz) {
        quizValidationRules.forEach(quizValidationRule -> quizValidationRule.validate(newQuiz));
    }
}
