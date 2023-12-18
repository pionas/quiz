package info.pionas.quiz.domain.exam.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamRepository {

    void save(NewExamDetails newExamDetails);

    Optional<ExamResult> getById(UUID id);

    List<ExamResult> getUserExams(String username);
}
