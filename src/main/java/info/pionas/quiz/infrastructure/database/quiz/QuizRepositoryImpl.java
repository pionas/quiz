package info.pionas.quiz.infrastructure.database.quiz;

import info.pionas.quiz.domain.quiz.api.Quiz;
import info.pionas.quiz.domain.quiz.api.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class QuizRepositoryImpl implements QuizRepository {

    private final QuizJpaRepository quizJpaRepository;
    private final QuizJpaMapper quizJpaMapper;

    @Override
    public Quiz save(Quiz quiz) {
        return quizJpaMapper.map(quizJpaRepository.save(quizJpaMapper.map(quiz)));
    }

    @Override
    public Optional<Quiz> findById(UUID id) {
        return quizJpaRepository.findById(id)
                .map(quizJpaMapper::map);
    }

    @Override
    public Boolean existById(UUID id) {
        return quizJpaRepository.existsById(id);
    }

    @Override
    public List<Quiz> getLastAdded() {
        return quizJpaRepository.findFirst5ByOrderByIdDesc()
                .stream()
                .map(quizJpaMapper::map)
                .toList();
    }
}
