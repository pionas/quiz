package info.pionas.quiz.infrastructure.database.quiz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface QuizAnswerJpaRepository extends CrudRepository<AnswerEntity, UUID> {

    @Query("SELECT count(ae)>0 from AnswerEntity ae WHERE ae.questionEntity.id = :questionId AND ae.id = :answerId AND ae.correct = true")
    Boolean isCorrectAnswer(@Param("questionId") UUID questionId, @Param("answerId") UUID answerId);
}
