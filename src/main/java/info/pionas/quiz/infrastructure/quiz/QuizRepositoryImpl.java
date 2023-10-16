package info.pionas.quiz.infrastructure.quiz;

import info.pionas.quiz.domain.quiz.api.Quiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
class QuizRepositoryImpl implements QuizRepository {

    private final List<Quiz> quizzes = Collections.emptyList();

    @Override
    public Quiz addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        return quiz;
    }
}
