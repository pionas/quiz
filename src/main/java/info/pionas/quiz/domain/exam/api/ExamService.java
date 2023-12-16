package info.pionas.quiz.domain.exam.api;

import java.util.List;
import java.util.UUID;

public interface ExamService {

    ExamResult endExam(String username, UUID uuid, List<PassExamAnswer> answers);
}
