package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Question {

    private final UUID id;
    private final String content;
    private final List<Answer> answers;
}
