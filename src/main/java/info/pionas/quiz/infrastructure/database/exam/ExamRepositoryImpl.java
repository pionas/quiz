package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.ExamRepository;
import info.pionas.quiz.domain.exam.api.ExamResult;
import info.pionas.quiz.domain.exam.api.NewExamDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
class ExamRepositoryImpl implements ExamRepository {

    private final ExamJpaRepository examJpaRepository;
    private final ExamResultJpaRepository examResultJpaRepository;
    private final ExamJpaMapper examJpaMapper;

    @Override
    public void save(NewExamDetails newExamDetails) {
        examJpaRepository.save(examJpaMapper.map(newExamDetails));
    }

    @Override
    public Optional<ExamResult> getById(UUID id) {
        return examResultJpaRepository.findById(id)
                .map(examJpaMapper::map);
    }

    @Override
    public List<ExamResult> getUserExams(String username) {
        return examJpaMapper.map(examResultJpaRepository.findAllByUsername(username));
    }
}
