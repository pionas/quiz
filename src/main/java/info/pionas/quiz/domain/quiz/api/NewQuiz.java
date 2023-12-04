package info.pionas.quiz.domain.quiz.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NewQuiz {

    private final String username;
    private final String title;
    private final String description;
    private final List<NewQuestion> questions;
}
