package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface QuizJpaRepository extends JpaRepository<QuizEntity, UUID> {
}
