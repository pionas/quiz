package info.pionas.quiz.infrastructure.database.quiz;

import info.pionas.quiz.domain.quiz.api.QuizAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
class QuizAnswerRepositoryImpl implements QuizAnswerRepository {

    private final QuizAnswerJpaRepository quizAnswerJpaRepository;

    @Override
    public boolean isCorrectAnswer(UUID questionId, UUID answerId) {
        return quizAnswerJpaRepository.isCorrectAnswer(questionId, answerId);
    }
}
