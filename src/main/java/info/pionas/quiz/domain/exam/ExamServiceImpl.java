package info.pionas.quiz.domain.exam;

import info.pionas.quiz.domain.exam.api.*;
import info.pionas.quiz.domain.quiz.api.QuizAnswerRepository;
import info.pionas.quiz.domain.shared.DateTimeProvider;
import info.pionas.quiz.domain.shared.UuidGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ExamAnswerRepository examAnswerRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final EndExamValidator endExamValidator;
    private final UuidGenerator uuidGenerator;
    private final DateTimeProvider dateTimeProvider;

    @Override
    @Transactional
    public UUID endExam(String username, UUID quizId, List<PassExamAnswer> answers) {
        endExamValidator.validate(quizId, answers);
        final var resultId = uuidGenerator.generate();
        final var dateTime = dateTimeProvider.now();
        examRepository.save(getNewExamDetails(resultId, quizId, username, dateTime));
        examAnswerRepository.saveAll(getExamAnswers(resultId, answers, dateTime));
        return resultId;
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResult getExamDetails(UUID id) {
        return examRepository.getById(id)
                .orElseThrow(() -> new ExamResultNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResult> getUserExams(String username) {
        return examRepository.getUserExams(username);
    }

    private NewExamDetails getNewExamDetails(UUID resultId, UUID quizId, String username, LocalDateTime dateTime) {
        return NewExamDetails.builder()
                .id(resultId)
                .username(username)
                .quizId(quizId)
                .created(dateTime)
                .build();
    }

    private List<NewExamAnswer> getExamAnswers(UUID resultId, List<PassExamAnswer> answers, LocalDateTime dateTime) {
        return Optional.ofNullable(answers)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(answer -> prepareNewExamAnswer(resultId, answer, dateTime))
                .toList();
    }

    private NewExamAnswer prepareNewExamAnswer(UUID resultId, PassExamAnswer answer, LocalDateTime dateTime) {
        final var correctAnswer = quizAnswerRepository.isCorrectAnswer(answer.getQuestionId(), answer.getAnswerId());
        return new NewExamAnswer(resultId, answer.getQuestionId(), answer.getAnswerId(), correctAnswer, dateTime);

    }

}
