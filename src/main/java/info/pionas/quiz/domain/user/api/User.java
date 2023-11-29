package info.pionas.quiz.domain.user.api;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private String username;
    private String password;
    private List<Role> roles;

}
