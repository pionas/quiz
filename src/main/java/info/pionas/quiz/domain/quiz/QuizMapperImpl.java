package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.QuizMapper;
import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class QuizMapperImpl implements QuizMapper {

    @Override
    public Quiz map(NewQuiz quiz, UuidGenerator uuidGenerator) {
        return Quiz.builder()
                .id(uuidGenerator.generate())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .questions(getQuestions(quiz.getQuestions(), uuidGenerator))
                .build();
    }

    @Override
    public Question map(NewQuestion question, UuidGenerator uuidGenerator) {
        return getQuestion(question, uuidGenerator);
    }

    private List<Question> getQuestions(List<NewQuestion> questions, UuidGenerator uuidGenerator) {
        return Optional.ofNullable(questions)
                .stream()
                .flatMap(Collection::stream)
                .map(newQuestion -> getQuestion(newQuestion, uuidGenerator))
                .collect(Collectors.toList());
    }

    private Question getQuestion(NewQuestion newQuestion, UuidGenerator uuidGenerator) {
        return Question.builder()
                .id(uuidGenerator.generate())
                .content(newQuestion.getContent())
                .answers(getAnswers(newQuestion.getAnswers(), uuidGenerator))
                .build();
    }

    private List<Answer> getAnswers(List<NewAnswer> answers, UuidGenerator uuidGenerator) {
        return Optional.ofNullable(answers)
                .stream()
                .flatMap(Collection::stream)
                .map(newAnswer -> getAnswer(newAnswer, uuidGenerator))
                .collect(Collectors.toList());
    }

    private Answer getAnswer(NewAnswer newAnswer, UuidGenerator uuidGenerator) {
        return Answer.builder()
                .id(uuidGenerator.generate())
                .content(newAnswer.getContent())
                .correct(newAnswer.isCorrect())
                .build();
    }
}
