package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuestion;
import info.pionas.quiz.domain.quiz.api.NewQuiz;
import info.pionas.quiz.domain.quiz.api.Quiz;

import java.util.UUID;

public interface QuizService {

    Quiz createQuiz(NewQuiz quiz);

    Quiz addQuestionToQuiz(UUID quizId, NewQuestion question);
}
