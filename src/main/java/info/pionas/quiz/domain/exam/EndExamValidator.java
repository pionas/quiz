package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.PassExamAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
class EndExamValidator {

    private final List<EndExamValidationRule> examValidationRuleList;

    void validate(UUID quizId, List<PassExamAnswer> answers) {
        examValidationRuleList.forEach(endExamValidationRule -> endExamValidationRule.validate(quizId, answers));
    }
}
