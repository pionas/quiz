package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.ExamDetails;
import info.pionas.quiz.domain.exam.api.ExamRepository;
import info.pionas.quiz.domain.exam.api.ExamResult;
import info.pionas.quiz.domain.exam.api.NewExamDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
class ExamRepositoryImpl implements ExamRepository {

    private final ExamJpaRepository examJpaRepository;
    private final ExamJpaMapper examJpaMapper;

    @Override
    public void save(NewExamDetails newExamDetails) {
        examJpaRepository.save(examJpaMapper.map(newExamDetails));
    }

    @Override
    public ExamResult getById(UUID id) {
        return null;
    }
}
