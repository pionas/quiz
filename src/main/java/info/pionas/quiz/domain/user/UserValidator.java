package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
class UserValidator {

    private final List<UserValidationRule> userValidationRules;

    public void validate(NewUser user) {
        userValidationRules.forEach(userValidationRule -> userValidationRule.validate(user));
    }
}
