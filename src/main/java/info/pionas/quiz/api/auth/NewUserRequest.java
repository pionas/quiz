package info.pionas.quiz.api.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class NewUserRequest {

    @NotBlank
    @Size(min = 3, max = 500)
    private String username;
    @NotBlank
    @Size(min = 3, max = 500)
    private String password;
    @NotBlank
    @Size(min = 3, max = 500)
    private String confirmPassword;
}