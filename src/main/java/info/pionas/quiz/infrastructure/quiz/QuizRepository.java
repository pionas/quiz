package info.pionas.quiz.infrastructure.quiz;

import info.pionas.quiz.domain.quiz.api.Quiz;

public interface QuizRepository {

    Quiz addQuiz(Quiz quiz);
}
