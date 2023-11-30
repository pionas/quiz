package info.pionas.quiz.domain.quiz.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NewQuestion {

    private final String content;
    private final List<NewAnswer> answers;
}
