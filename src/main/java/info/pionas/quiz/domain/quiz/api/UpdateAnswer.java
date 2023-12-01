package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
public class UpdateAnswer {

    @Setter
    private UUID id;
    private String content;
    private boolean correct;
}
