package info.pionas.quiz.domain.quiz.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizRepository {

    Quiz save(Quiz quiz);

    Optional<Quiz> findById(UUID id);

    Boolean existById(UUID id);

    List<Quiz> getLastAdded();
}
