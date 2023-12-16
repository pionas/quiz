package info.pionas.quiz.api.quiz.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class QuestionResponseDto {

    private UUID id;
    private String content;
    private List<AnswerResponseDto> answers;
}
