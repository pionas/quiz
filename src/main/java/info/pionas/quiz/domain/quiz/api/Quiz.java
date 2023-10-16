package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Quiz {

    private final UUID id;
    private final String title;
    private final String description;
    private final List<Question> questions;
}
