package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuiz;
import info.pionas.quiz.domain.quiz.api.Quiz;

public interface QuizService {

    Quiz createQuiz(NewQuiz quiz);
}
