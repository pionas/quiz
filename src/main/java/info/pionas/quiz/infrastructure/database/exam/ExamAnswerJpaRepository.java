package info.pionas.quiz.infrastructure.database.exam;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface ExamAnswerJpaRepository extends CrudRepository<ExamAnswerEntity, UUID> {

}
