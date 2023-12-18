package info.pionas.quiz.api.exam;

import info.pionas.quiz.api.exam.api.ExamResponseDto;
import info.pionas.quiz.api.exam.api.NewExamAnswerDto;
import info.pionas.quiz.domain.exam.api.ExamResult;
import info.pionas.quiz.domain.exam.api.PassExamAnswer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
interface ExamApiMapper {

    ExamResponseDto map(ExamResult examResult);

    List<PassExamAnswer> map(List<NewExamAnswerDto> answers);

    List<ExamResponseDto> mapToDto(List<ExamResult> userExams);
}
