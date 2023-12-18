package info.pionas.quiz.domain.exam.api;

import java.util.List;
import java.util.UUID;

public interface ExamService {

    UUID endExam(String username, UUID uuid, List<PassExamAnswer> answers);

    ExamResult getExamDetails(UUID examId);
}
