package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.PassExamAnswer;

import java.util.List;
import java.util.UUID;

interface EndExamValidationRule {

    void validate(UUID quizId, List<PassExamAnswer> answers);
}
