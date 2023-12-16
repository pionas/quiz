package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.ExamAnswerRepository;
import info.pionas.quiz.domain.exam.api.NewExamAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
class ExamAnswerRepositoryImpl implements ExamAnswerRepository {

    private final ExamAnswerJpaRepository examAnswerJpaRepository;
    private final ExamAnswerJpaMapper examAnswerJpaMapper;

    @Override
    public void saveAll(List<NewExamAnswer> newExamAnswers) {
        examAnswerJpaRepository.saveAll(examAnswerJpaMapper.map(newExamAnswers));
    }
}
