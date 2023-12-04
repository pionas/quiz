package info.pionas.quiz.domain.user.api;

import lombok.*;

import java.util.List;
import java.util.Objects;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUser {

    private String username;
    private String password;
    private String confirmPassword;
    private List<Role> roles;

    public boolean hasPasswordMatches() {
        return Objects.equals(password, confirmPassword);
    }
}
