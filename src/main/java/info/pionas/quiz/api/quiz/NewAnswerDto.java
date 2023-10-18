package info.pionas.quiz.api.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
class NewAnswerDto {

    @NotBlank
    @Size(max = 150)
    private String content;
    @NotNull
    private Boolean correct;
}
