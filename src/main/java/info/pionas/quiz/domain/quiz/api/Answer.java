package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Answer {

    private final UUID id;
    private final String content;
    private final boolean correct;
}
