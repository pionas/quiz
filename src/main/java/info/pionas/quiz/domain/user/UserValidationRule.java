package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;

interface UserValidationRule {

    void validate(NewUser user);
}
