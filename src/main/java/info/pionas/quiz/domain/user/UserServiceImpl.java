package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;
import info.pionas.quiz.domain.user.api.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.get(username);
    }

    @Override
    public User addUser(NewUser user) {
        userValidator.validate(user);
        final var password = passwordEncoder.encode(user.getPassword());
        return userRepository.save(new User(user.getUsername(), password, user.getRoles()));
    }
}
