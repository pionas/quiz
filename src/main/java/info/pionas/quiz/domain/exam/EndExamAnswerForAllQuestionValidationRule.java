package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.AnswerForQuestionNotFoundException;
import info.pionas.quiz.domain.exam.api.PassExamAnswer;
import info.pionas.quiz.domain.quiz.api.Question;
import info.pionas.quiz.domain.quiz.api.Quiz;
import info.pionas.quiz.domain.quiz.api.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
class EndExamAnswerForAllQuestionValidationRule implements EndExamValidationRule {

    private final QuizRepository quizRepository;

    @Override
    public void validate(UUID quizId, List<PassExamAnswer> answers) {
        quizRepository.findById(quizId)
                .map(Quiz::getQuestions)
                .orElseGet(Collections::emptyList)
                .forEach(question -> checkQuestionHasAnswer(question, answers));
    }

    private void checkQuestionHasAnswer(Question question, List<PassExamAnswer> answers) {
        final var questionId = question.getId();
        boolean existAnswerForQuestion = Optional.ofNullable(answers)
                .orElseGet(Collections::emptyList)
                .stream()
                .anyMatch(passExamAnswer -> passExamAnswer.isAnswerForQuestion(questionId));
        if (!existAnswerForQuestion) {
            throw new AnswerForQuestionNotFoundException(questionId);
        }
    }
}
