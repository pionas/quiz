package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
interface QuizMapper {

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Quiz mapToQuiz(NewQuiz quiz, @Context UuidGenerator uuidGenerator);

//    List<Question> mapToQuestions(List<NewQuestion> newQuestions, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Question mapToQuestion(NewQuestion question, @Context UuidGenerator uuidGenerator);

//    List<Answer> mapToAnswers(List<NewAnswer> newAnswers, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Answer mapToAnswer(NewAnswer answer, @Context UuidGenerator uuidGenerator);

}
