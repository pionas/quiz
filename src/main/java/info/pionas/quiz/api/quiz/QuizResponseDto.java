package info.pionas.quiz.api.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
class QuizResponseDto {

    private UUID id;
    private String title;
    private String description;
    private List<QuestionDto> questions = new ArrayList<>();
}
