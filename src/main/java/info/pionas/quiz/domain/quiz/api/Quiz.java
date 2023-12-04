package info.pionas.quiz.domain.quiz.api;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
public class Quiz {

    private final UUID id;
    private final String title;
    private final String description;
    @Builder.Default
    private final List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(UUID questionId) {
        questions.removeIf(question -> isEquals(questionId, question));
    }

    public void updateQuestion(UUID questionId, String content, List<Answer> answers) {
        questions.stream()
                .filter(question -> isEquals(questionId, question))
                .forEach(question -> question.update(content, answers));
    }

    private boolean isEquals(UUID questionId, Question question) {
        return Objects.equals(question.getId(), questionId);
    }
}
