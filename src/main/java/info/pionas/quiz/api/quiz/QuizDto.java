package info.pionas.quiz.api.quiz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
class QuizDto {

    private UUID id;
    private String title;
    private String description;
    private List<QuestionDto> questions = new ArrayList<>();
}
