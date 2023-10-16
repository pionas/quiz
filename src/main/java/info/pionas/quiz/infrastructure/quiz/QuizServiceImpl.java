package info.pionas.quiz.infrastructure.quiz;

import info.pionas.quiz.domain.quiz.QuizMapper;
import info.pionas.quiz.domain.quiz.QuizService;
import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class QuizServiceImpl implements QuizService {

    private final UuidGenerator uuidGenerator;
    private final QuizMapper mapper;

    @Override
    public Quiz createQuiz(NewQuiz quiz) {
        return mapper.map(quiz, uuidGenerator);
    }

}
