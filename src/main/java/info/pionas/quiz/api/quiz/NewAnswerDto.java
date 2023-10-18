package info.pionas.quiz.api.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
class NewAnswerDto {

    @NotBlank
    @Size(min = 3, max = 500)
    private String content;
    @NotNull
    @Pattern(regexp = "^true$|^false$")
    private Boolean correct;
}
