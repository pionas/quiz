package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;
import info.pionas.quiz.domain.user.api.UserPasswordNotMatchedException;
import org.springframework.stereotype.Component;

@Component
class UserPasswordMatchedValidationRule implements UserValidationRule {

    @Override
    public void validate(NewUser user) {
        if (!user.hasPasswordMatches()) {
            throw new UserPasswordNotMatchedException();
        }
    }
}
