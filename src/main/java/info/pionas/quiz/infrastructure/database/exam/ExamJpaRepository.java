package info.pionas.quiz.infrastructure.database.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface ExamJpaRepository extends JpaRepository<ExamEntity, UUID> {
}