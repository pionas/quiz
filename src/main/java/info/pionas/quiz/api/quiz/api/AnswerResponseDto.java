package info.pionas.quiz.api.quiz.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class AnswerResponseDto {

    private UUID id;
    private String content;
    private Boolean correct;
}
