package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.shared.exception.NotFoundException;

import java.util.UUID;

public class QuizNotFoundException extends NotFoundException {

    public QuizNotFoundException(UUID quizId) {
        super(String.format("Quiz by ID %s not exist", quizId));
    }
}
