package info.pionas.quiz.domain.exam.api;

import java.util.UUID;

public interface ExamRepository {

    void save(NewExamDetails newExamDetails);

    ExamResult getById(UUID id);
}
