package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface QuizMapper {

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Quiz mapToQuiz(NewQuiz quiz, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Question mapToQuestion(NewQuestion question, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Answer mapToAnswer(NewAnswer answer, @Context UuidGenerator uuidGenerator);

}
