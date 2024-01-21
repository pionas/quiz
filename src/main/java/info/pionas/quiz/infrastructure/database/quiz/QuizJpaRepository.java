package info.pionas.quiz.infrastructure.database.quiz;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface QuizJpaRepository extends CrudRepository<QuizEntity, UUID> {

    List<QuizEntity> findFirst5ByOrderByIdDesc();
}
