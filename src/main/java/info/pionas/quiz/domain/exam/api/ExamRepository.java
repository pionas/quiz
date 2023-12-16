package info.pionas.quiz.domain.exam.api;

import java.util.UUID;

public interface ExamRepository {

    ExamResult save(UUID id, ExamDetails examDetails);
}
