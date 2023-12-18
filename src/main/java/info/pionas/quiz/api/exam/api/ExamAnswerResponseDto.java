package info.pionas.quiz.api.exam.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ExamAnswerResponseDto {

    private UUID questionId;
    private UUID answerId;
    private Boolean correct;
}
