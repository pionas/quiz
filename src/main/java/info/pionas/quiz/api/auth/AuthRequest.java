package info.pionas.quiz.api.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class AuthRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}