package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UuidGenerator uuidGenerator;
    private final QuizMapper mapper;
    private final QuizValidator quizValidator;

    @Override
    public Quiz createQuiz(NewQuiz quiz) {
        quizValidator.validate(quiz);
        return quizRepository.save(mapper.mapToQuiz(quiz, uuidGenerator));
    }

    @Override
    @Transactional
    public Quiz addQuestionToQuiz(UUID quizId, NewQuestion question) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    quiz.addQuestion(mapper.mapToQuestion(question, uuidGenerator));
                    return quiz;
                })
                .map(quizRepository::save)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
    }

    @Override
    @Transactional
    public Quiz removeQuestionFromQuiz(UUID quizId, UUID questionId) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    quiz.removeQuestion(questionId);
                    return quiz;
                })
                .map(quizRepository::save)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
    }

    @Override
    @Transactional
    public Quiz updateQuestionFromQuiz(UUID quizId, UpdateQuestion question) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    quiz.updateQuestion(question.getId(), question.getContent(), mapper.mapToAnswers(question.getAnswers(), uuidGenerator));
                    return quiz;
                })
                .map(quizRepository::save)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Quiz> getLastAdded() {
        return quizRepository.getLastAdded();
    }

}
