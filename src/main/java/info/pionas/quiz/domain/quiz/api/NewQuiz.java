package info.pionas.quiz.domain.quiz.api;

import lombok.Data;

import java.util.List;

@Data
public class NewQuiz {

    private final String title;
    private final String description;
    private final List<NewQuestion> questions;
}
