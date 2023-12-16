package info.pionas.quiz.domain.exam;


import info.pionas.quiz.domain.exam.api.AnswerForQuestionNotFoundException;
import info.pionas.quiz.domain.exam.api.ExamAnswer;
import info.pionas.quiz.domain.exam.api.ExamDetails;
import info.pionas.quiz.domain.exam.api.PassExamAnswer;
import info.pionas.quiz.domain.quiz.api.Question;
import info.pionas.quiz.domain.quiz.api.Quiz;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class ExamFactory {

    public ExamDetails endExam(String username, Quiz quiz, List<PassExamAnswer> answers) {
        return ExamDetails.of(username, quiz, getAnswerResult(quiz.getQuestions(), answers));
    }

    private List<ExamAnswer> getAnswerResult(List<Question> questions, List<PassExamAnswer> answers) {
        return Optional.ofNullable(questions)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(question -> ExamAnswer.of(question, getAnswerForQuestion(question, answers)))
                .toList();
    }

    private PassExamAnswer getAnswerForQuestion(Question question, List<PassExamAnswer> answers) {
        final var questionId = question.getId();
        return Optional.ofNullable(answers)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(passExamAnswer -> passExamAnswer.isAnswerForQuestion(questionId))
                .findAny()
                .orElseThrow(() -> new AnswerForQuestionNotFoundException(questionId));
    }
}
