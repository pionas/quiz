package info.pionas.quiz.domain.exam.api;

import info.pionas.quiz.domain.shared.exception.NotFoundException;

import java.util.UUID;

public class ExamResultNotFoundException extends NotFoundException {

    public ExamResultNotFoundException(UUID quizId) {
        super(String.format("Exam result by ID %s not exist", quizId));
    }
}
