package info.pionas.quiz.api.quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
class NewQuizDto {

    @NotBlank
    @Size(min = 3, max = 500)
    private String title;
    @Size(min = 3, max = 500)
    private String description;
    @Valid
    private List<NewQuestionDto> questions = new ArrayList<>();
}
