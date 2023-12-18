package info.pionas.quiz.api.exam.api;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class NewExamAnswerDto {

    @NotNull
    private UUID questionId;
    @NotNull
    private UUID answerId;
}
