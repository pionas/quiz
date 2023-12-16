package info.pionas.quiz.domain.exam.api;

import java.util.List;

public interface ExamAnswerRepository {

    void saveAll(List<NewExamAnswer> answers);
}
