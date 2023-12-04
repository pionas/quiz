package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> get(String username);

    User save(User user);

    boolean existByUsername(String username);
}
