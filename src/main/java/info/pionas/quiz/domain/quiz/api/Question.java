package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Question {

    private final UUID id;
    private final String content;
    private final List<Answer> answers;
}
