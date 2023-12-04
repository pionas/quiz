package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;
import info.pionas.quiz.domain.user.api.UserFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
class UsernameValidationRule implements UserValidationRule {

    private final UserRepository userRepository;

    @Override
    public void validate(NewUser user) {
        if (userRepository.existByUsername(user.getUsername())) {
            throw new UserFoundException(user.getUsername());
        }
    }
}
