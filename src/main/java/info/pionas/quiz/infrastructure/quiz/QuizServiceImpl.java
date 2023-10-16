package info.pionas.quiz.infrastructure.quiz;

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

    @Override
    public Quiz createQuiz(NewQuiz quiz) {
        return Quiz.builder()
                .id(uuidGenerator.generate())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .questions(getQuestions(quiz.getQuestions()))
                .build();
    }

    private List<Question> getQuestions(List<NewQuestion> questions) {
        return Optional.ofNullable(questions)
                .stream()
                .flatMap(Collection::stream)
                .map(this::getQuestion)
                .collect(Collectors.toList());
    }

    private Question getQuestion(NewQuestion newQuestion) {
        return Question.builder()
                .id(uuidGenerator.generate())
                .content(newQuestion.getContent())
                .answers(getAnswers(newQuestion.getAnswers()))
                .build();
    }

    private List<Answer> getAnswers(List<NewAnswer> answers) {
        return Optional.ofNullable(answers)
                .stream()
                .flatMap(Collection::stream)
                .map(this::getAnswer)
                .collect(Collectors.toList());
    }

    private Answer getAnswer(NewAnswer newAnswer) {
        return Answer.builder()
                .id(uuidGenerator.generate())
                .content(newAnswer.getContent())
                .correct(newAnswer.isCorrect())
                .build();
    }
}
