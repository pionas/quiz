package info.pionas.quiz.domain.quiz;

import java.util.UUID;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(UUID quizId) {
        super(String.format("Quiz by ID %s not exist", quizId));
    }
}
