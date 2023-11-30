package info.pionas.quiz.domain.quiz.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewAnswer {

    private final String content;
    private final boolean correct;
}
