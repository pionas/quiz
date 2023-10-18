package info.pionas.quiz.api.quiz;

import lombok.Data;

import java.util.UUID;

@Data
class AnswerDto {

    private UUID id;
    private String content;
    private boolean correct;
}
