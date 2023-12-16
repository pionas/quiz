package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.PassExamAnswer;
import info.pionas.quiz.domain.exam.api.ExamRepository;
import info.pionas.quiz.domain.exam.api.ExamResult;
import info.pionas.quiz.domain.exam.api.ExamService;
import info.pionas.quiz.domain.quiz.QuizNotFoundException;
import info.pionas.quiz.domain.quiz.api.QuizRepository;
import info.pionas.quiz.domain.shared.UuidGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
class ExamServiceImpl implements ExamService {

    private final QuizRepository quizRepository;
    private final ExamRepository examRepository;
    private final ExamFactory examFactory;
    private final UuidGenerator uuidGenerator;

    @Override
    public ExamResult endExam(String username, UUID quizId, List<PassExamAnswer> answers) {
        final var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
        return examRepository.save(uuidGenerator.generate(), examFactory.endExam(username, quiz, answers));
    }
}
