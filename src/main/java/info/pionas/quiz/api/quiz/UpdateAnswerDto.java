package info.pionas.quiz.api.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
class UpdateAnswerDto {

    private UUID id;
    @NotBlank
    @Size(max = 150)
    private String content;
    @NotNull
    private Boolean correct;
}