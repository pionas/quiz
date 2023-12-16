package info.pionas.quiz.domain.exam.api;

import info.pionas.quiz.domain.quiz.api.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
public class ExamDetails {

    private String username;
    private Quiz quiz;
    private List<ExamAnswer> answers;
}
