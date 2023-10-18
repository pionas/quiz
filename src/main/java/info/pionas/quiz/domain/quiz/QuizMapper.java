package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.NewQuestion;
import info.pionas.quiz.domain.quiz.api.NewQuiz;
import info.pionas.quiz.domain.quiz.api.Question;
import info.pionas.quiz.domain.quiz.api.Quiz;
import info.pionas.quiz.domain.shared.UuidGenerator;

public interface QuizMapper {

    Quiz map(NewQuiz quiz, UuidGenerator uuidGenerator);

    Question map(NewQuestion question, UuidGenerator uuidGenerator);
}
