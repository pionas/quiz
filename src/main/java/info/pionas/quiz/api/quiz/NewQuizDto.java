package info.pionas.quiz.api.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class NewQuizDto {

    @NotBlank
    private String title;
    @Size(min = 3, max = 500)
    private String description;
    private List<NewQuestionDto> questions = new ArrayList<>();
}
