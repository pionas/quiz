package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findByUsername(String username);
}
