package info.pionas.quiz.infrastructure.database.exam;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface ExamResultJpaRepository extends CrudRepository<ExamResultReadModel, UUID> {

    List<ExamResultReadModel> findAllByUsername(String username);
}