package info.pionas.quiz.api.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
class QuestionResponseDto {

    private UUID id;
    private String content;
    private List<AnswerResponseDto> answers;
}
