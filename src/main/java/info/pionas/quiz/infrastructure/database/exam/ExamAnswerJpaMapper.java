package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.NewExamAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
interface ExamAnswerJpaMapper {

    @Mapping(target = "id.resultId", source = "resultId")
    @Mapping(target = "id.questionId", source = "questionId")
    @Mapping(target = "id.answerId", source = "answerId")
    @Mapping(target = "correct", source = "correct")
    @Mapping(target = "created", source = "created")
    ExamAnswerEntity map(NewExamAnswer newExamAnswer);

    List<ExamAnswerEntity> map(List<NewExamAnswer> newExamAnswers);

}
