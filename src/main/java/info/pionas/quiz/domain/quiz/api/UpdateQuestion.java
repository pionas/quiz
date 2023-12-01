package info.pionas.quiz.domain.quiz.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateQuestion {

    private final UUID id;
    private final String content;
    private final List<UpdateAnswer> answers;
}
