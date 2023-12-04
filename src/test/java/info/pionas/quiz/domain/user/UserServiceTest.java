package info.pionas.quiz.domain.user;

import info.pionas.quiz.domain.user.api.NewUser;
import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import info.pionas.quiz.domain.user.api.UserFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserValidator userValidator = new UserValidator(List.of(new UsernameValidationRule(userRepository), new UserPasswordMatchedValidationRule()));
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final UserService service = new UserServiceImpl(userRepository, userValidator, passwordEncoder);

    @Test
    void should_create_user() {
        //given
        final var newUser = new NewUser("username", "password", "password", List.of(Role.ROLE_USER));
        when(userRepository.existByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodePassword");
        //when
        final var user = service.addUser(newUser);
        //then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(user.getPassword()).isEqualTo("encodePassword");
        assertThat(user.getRoles()).isEqualTo(newUser.getRoles());
    }

    @Test
    void should_throw_not_found_exception_when_quiz_by_id_not_exist() {
        //given
        final var newUser = new NewUser("username", "password", "password", List.of(Role.ROLE_USER));
        when(userRepository.existByUsername(newUser.getUsername())).thenReturn(true);
        //when
        UserFoundException exception = assertThrows(UserFoundException.class, () -> service.addUser(newUser));
        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo(String.format("Username %s already in use", newUser.getUsername()));
    }

    @Test
    void should_return_user() {
        //given
        final var username = "username";
        final var expectedUser = new User(username, "password", List.of(Role.ROLE_USER));
        when(userRepository.get(username)).thenReturn(Optional.of(expectedUser));
        //when
        Optional<User> userDetails = service.findByUsername(username);
        //then
        assertThat(userDetails).isPresent();
        User user = userDetails.get();
        assertThat(user.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(user.getRoles()).isEqualTo(expectedUser.getRoles());
    }

    @Test
    void should_return_empty_when_user_by_username_not_exist() {
        //given
        final var username = "username";
        when(userRepository.get(username)).thenReturn(Optional.empty());
        //when
        Optional<User> userDetails = service.findByUsername(username);
        //then
        assertThat(userDetails).isNotPresent();
    }

}