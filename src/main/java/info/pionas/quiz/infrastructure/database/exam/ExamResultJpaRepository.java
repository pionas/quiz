package info.pionas.quiz.infrastructure.database.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface ExamResultJpaRepository extends JpaRepository<ExamResultReadModel, UUID> {

    List<ExamResultReadModel> findAllByUsername(String username);
}