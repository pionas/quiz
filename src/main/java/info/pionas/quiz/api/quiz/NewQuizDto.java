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
class NewQuizDto {

    @NotBlank
    @Size(min = 3, max = 500)
    private String title;
    @Size(min = 3, max = 500)
    private String description;
    @Valid
    @Builder.Default
    private List<NewQuestionDto> questions = new ArrayList<>();
}
