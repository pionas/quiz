package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.Quiz;
import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import org.springframework.stereotype.Component;

@Component
class QuizJpaMapper {

    public Quiz map(QuizEntity quizEntity) {
        return null;
    }

    public QuizEntity map(Quiz quiz) {
        return null;
    }
}
