package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.ExamResult;
import info.pionas.quiz.domain.exam.api.NewExamDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
interface ExamJpaMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "quizId", source = "quizId")
    @Mapping(target = "created", source = "created")
    ExamEntity map(NewExamDetails newExamDetails);

    ExamResult map(ExamResultReadModel examResultReadModel);

    List<ExamResult> map(List<ExamResultReadModel> examResultReadModels);
}
