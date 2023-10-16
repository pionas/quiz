package info.pionas.quiz.infrastructure.quiz;

import info.pionas.quiz.domain.quiz.api.Quiz;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepository {

    Quiz addQuiz(Quiz quiz);

    Optional<Quiz> findById(UUID uuid);
}
