package info.pionas.quiz.api.quiz;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
class QuestionDto {

    private UUID id;
    private String content;
    private List<AnswerDto> answers;
}
