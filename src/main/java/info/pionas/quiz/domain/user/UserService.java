package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;
import info.pionas.quiz.domain.user.api.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    User addUser(NewUser user);
}
