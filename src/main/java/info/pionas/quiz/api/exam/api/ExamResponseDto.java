package info.pionas.quiz.api.exam.api;

import info.pionas.quiz.api.quiz.api.QuizResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ExamResponseDto {

    private UUID id;
    private QuizResponseDto quiz;
    private List<ExamAnswerResponseDto> answers;
    private LocalDateTime created;
    private Long correctAnswer;
}
