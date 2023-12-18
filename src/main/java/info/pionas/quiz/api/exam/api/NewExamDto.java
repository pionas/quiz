package info.pionas.quiz.api.exam.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class NewExamDto {

    @NotNull
    private UUID quizId;
    @Valid
    @NotEmpty
    private List<NewExamAnswerDto> answers;
}
