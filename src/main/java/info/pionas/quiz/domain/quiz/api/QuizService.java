package info.pionas.quiz.domain.quiz.api;

import java.util.UUID;

public interface QuizService {

    Quiz createQuiz(NewQuiz quiz);

    Quiz addQuestionToQuiz(UUID quizId, NewQuestion question);

    Quiz removeQuestionFromQuiz(UUID quizId, UUID questionId);

    Quiz updateQuestionFromQuiz(UUID quizId, UpdateQuestion question);
}
