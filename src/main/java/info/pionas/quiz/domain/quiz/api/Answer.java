package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class Answer {

    @Setter
    private UUID id;
    private String content;
    private boolean correct;

    public boolean isAnswerFor(UUID answerId) {
        return Objects.equals(id, answerId);
    }
}
