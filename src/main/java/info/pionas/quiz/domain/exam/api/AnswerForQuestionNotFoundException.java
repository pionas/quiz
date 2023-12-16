package info.pionas.quiz.domain.exam.api;

import info.pionas.quiz.domain.shared.exception.NotFoundException;

import java.util.UUID;

public class AnswerForQuestionNotFoundException extends NotFoundException {
    public AnswerForQuestionNotFoundException(UUID questionId) {
        super(String.format("There is no answer to the question %s", questionId));
    }
}
