package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Question {

    private final UUID id;
    private String content;
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    public void update(String content, List<Answer> updateAnswers) {
        this.content = content;
        this.answers.clear();
        this.answers.addAll(updateAnswers);
    }
}
