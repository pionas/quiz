package info.pionas.quiz.infrastructure.database.user;

import info.pionas.quiz.domain.user.UserRepository;
import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
class UserRepositoryImpl implements UserRepository {

    private Map<String, User> data;

    @PostConstruct
    public void init() {
        data = new HashMap<>();
        //username:passwowrd -> user:user
        data.put("user", new User("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", List.of(Role.ROLE_USER)));
        //username:passwowrd -> admin:admin
        data.put("admin", new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", List.of(Role.ROLE_ADMIN)));
    }

    @Override
    public Optional<User> get(String username) {
        return Optional.ofNullable(data.get(username));
    }

    @Override
    public User save(User user) {
        data.put(user.getUsername(), user);
        return user;
    }

    @Override
    public boolean existByUsername(String username) {
        return data.containsKey(username);
    }
}
