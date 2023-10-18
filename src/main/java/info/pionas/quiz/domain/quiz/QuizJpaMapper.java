package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.Answer;
import info.pionas.quiz.domain.quiz.api.Question;
import info.pionas.quiz.domain.quiz.api.Quiz;
import info.pionas.quiz.infrastructure.database.quiz.AnswerEntity;
import info.pionas.quiz.infrastructure.database.quiz.QuestionEntity;
import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface QuizJpaMapper {

    Quiz map(QuizEntity quizEntity);

    QuizEntity map(Quiz quiz);

    Question map(QuestionEntity questionEntity);

    QuestionEntity map(Question question);

    Answer map(AnswerEntity answerEntity);

    QuizEntity map(Answer answer);
}
