package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuestion;
import info.pionas.quiz.domain.quiz.api.NewQuiz;
import info.pionas.quiz.domain.quiz.api.Quiz;
import info.pionas.quiz.domain.shared.UuidGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UuidGenerator uuidGenerator;
    private final QuizMapper mapper;

    @Override
    public Quiz createQuiz(NewQuiz quiz) {
        return quizRepository.save(mapper.mapToQuiz(quiz, uuidGenerator));
    }

    @Override
    @Transactional
    public Quiz addQuestionToQuiz(UUID quizId, NewQuestion question) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    quiz.addQuestion(mapper.mapToQuestion(question, uuidGenerator));
                    return quiz;
                }).map(quizRepository::save)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
    }

    @Override
    @Transactional
    public Quiz removeQuestionFromQuiz(UUID quizId, UUID questionId) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    quiz.removeQuestion(questionId);
                    return quiz;
                }).map(quizRepository::save)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
    }

}
