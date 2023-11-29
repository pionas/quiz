package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class UserServiceImpl implements UserService {

    private Map<String, User> data;

    @PostConstruct
    public void init() {
        data = new HashMap<>();
        //username:passwowrd -> user:user
        data.put("user", new User("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", List.of(Role.ROLE_USER)));
        //username:passwowrd -> admin:admin
        data.put("admin", new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", List.of(Role.ROLE_ADMIN)));
    }

    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(data.get(username));
    }
}
