package info.pionas.quiz.api.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
class NewQuestionDto {

    @NotBlank
    @Size(min = 3, max = 500)
    private String content;
    private List<NewAnswerDto> answers;
}
