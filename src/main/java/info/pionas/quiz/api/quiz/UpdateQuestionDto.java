package info.pionas.quiz.api.quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class UpdateQuestionDto {

    @NotNull
    private UUID id;
    @NotBlank
    @Size(min = 3, max = 500)
    private String content;
    @Valid
    private List<UpdateAnswerDto> answers = new ArrayList<>();
}
