package info.pionas.quiz.infrastructure.database.exam;

import info.pionas.quiz.domain.exam.api.NewExamAnswer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
interface ExamAnswerJpaMapper {

    ExamAnswerEntity map(NewExamAnswer newExamAnswer);

    List<ExamAnswerEntity> map(List<NewExamAnswer> newExamAnswers);

}
