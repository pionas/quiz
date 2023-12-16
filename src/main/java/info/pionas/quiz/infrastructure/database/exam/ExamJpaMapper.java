package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.NewExamDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ExamJpaMapper {

    ExamEntity map(NewExamDetails newExamDetails);
}
