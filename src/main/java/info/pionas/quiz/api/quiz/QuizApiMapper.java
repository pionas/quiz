package info.pionas.quiz.api.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface QuizApiMapper {

    NewQuiz map(NewQuizDto newQuizDto);

    NewQuestion map(NewQuestionDto newQuestionDto);

    NewAnswer map(NewAnswerDto newAnswerDto);

    QuizDto map(Quiz quiz);

    QuestionDto map(Question question);

    AnswerDto map(Answer answer);
}
