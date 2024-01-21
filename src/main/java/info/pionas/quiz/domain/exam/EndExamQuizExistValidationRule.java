package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.PassExamAnswer;
import info.pionas.quiz.domain.quiz.QuizNotFoundException;
import info.pionas.quiz.domain.quiz.api.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
class EndExamQuizExistValidationRule implements EndExamValidationRule {

    private final QuizRepository quizRepository;

    @Override
    public void validate(UUID quizId, List<PassExamAnswer> answers) {
        boolean exists = Boolean.TRUE.equals(quizRepository.existById(quizId));
        if (!exists) {
            throw new QuizNotFoundException(quizId);
        }
    }
}
