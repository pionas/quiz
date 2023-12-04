package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuiz;
import info.pionas.quiz.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class QuizUserValidationRule implements QuizValidationRule {

    private final UserRepository userRepository;

    @Override
    public void validate(NewQuiz newQuiz) {
        final var username = newQuiz.getUsername();
        if (!userRepository.existByUsername(username)) {
            throw new UsernameNotFoundException(String.format("User %s not exist", username));
        }
    }
}
