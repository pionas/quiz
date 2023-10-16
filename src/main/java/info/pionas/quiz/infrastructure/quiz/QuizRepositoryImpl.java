package info.pionas.quiz.infrastructure.quiz;

import info.pionas.quiz.domain.quiz.api.Quiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
class QuizRepositoryImpl implements QuizRepository {

    private final List<Quiz> quizzes = Collections.emptyList();

    @Override
    public Quiz save(Quiz quiz) {
        quizzes.add(quiz);
        return quiz;
    }

    @Override
    public Optional<Quiz> findById(UUID uuid) {
        return quizzes.stream()
                .filter(quiz -> Objects.equals(quiz.getId(), uuid))
                .findAny();
    }
}
