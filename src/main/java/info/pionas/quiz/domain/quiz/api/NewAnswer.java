package info.pionas.quiz.domain.quiz.api;

import lombok.Data;

@Data
public class NewAnswer {

    private final String content;
    private final boolean correct;
}
