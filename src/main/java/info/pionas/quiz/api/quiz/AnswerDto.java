package info.pionas.quiz.api.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
class AnswerDto {

    private UUID id;
    private String content;
    private Boolean correct;
}
