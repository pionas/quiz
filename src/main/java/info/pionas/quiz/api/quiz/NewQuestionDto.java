package info.pionas.quiz.api.quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
class NewQuestionDto {

    @NotBlank
    @Size(min = 3, max = 500)
    private String content;
    @Valid
    @Builder.Default
    private List<NewAnswerDto> answers = new ArrayList<>();
}
