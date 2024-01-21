package info.pionas.quiz.domain.quiz.api;

import java.util.UUID;

public interface QuizAnswerRepository {

    Boolean isCorrectAnswer(UUID questionId, UUID answerId);
}
