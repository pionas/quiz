package info.pionas.quiz.domain.quiz.api;

import lombok.Data;

import java.util.List;

@Data
public class NewQuestion {

    private final String content;
    private final List<NewAnswer> answers;
}
