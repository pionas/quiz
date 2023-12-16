package info.pionas.quiz.api.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface QuizApiMapper {

    NewQuiz map(String username, NewQuizDto newQuizDto);

    NewQuestion map(NewQuestionDto newQuestionDto);

    NewAnswer map(NewAnswerDto newAnswerDto);

    QuizResponseDto map(Quiz quiz);

    QuestionResponseDto map(Question question);

    AnswerResponseDto map(Answer answer);

    UpdateQuestion map(UpdateQuestionDto updateQuestionDto);

    UpdateAnswer map(UpdateAnswerDto updateAnswerDto);

}
