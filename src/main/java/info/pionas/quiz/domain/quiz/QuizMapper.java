package info.pionas.quiz.domain.quiz;

import info.pionas.quiz.domain.quiz.api.*;
import info.pionas.quiz.domain.shared.UuidGenerator;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring")
interface QuizMapper {

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Quiz mapToQuiz(NewQuiz quiz, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Question mapToQuestion(NewQuestion question, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(uuidGenerator.generate())")
    Answer mapToAnswer(NewAnswer answer, @Context UuidGenerator uuidGenerator);

    List<Answer> mapToAnswers(List<UpdateAnswer> answers, @Context UuidGenerator uuidGenerator);

    @Mapping(target = "id", expression = "java(mapAnswerId(answer, uuidGenerator))")
    Answer mapToAnswer(UpdateAnswer answer, @Context UuidGenerator uuidGenerator);

    default UUID mapAnswerId(UpdateAnswer answer, @Context UuidGenerator uuidGenerator) {
        return Optional.ofNullable(answer)
                .map(UpdateAnswer::getId)
                .orElse(uuidGenerator.generate());
    }
}
