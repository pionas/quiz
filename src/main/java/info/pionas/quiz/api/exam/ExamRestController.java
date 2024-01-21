package info.pionas.quiz.api.exam;

import info.pionas.quiz.api.exam.api.ExamResponseDto;
import info.pionas.quiz.api.exam.api.NewExamDto;
import info.pionas.quiz.domain.exam.api.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exam")
@RequiredArgsConstructor
class ExamRestController {

    private final ExamService examService;
    private final ExamApiMapper examApiMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ExamResponseDto endExam(@Valid @RequestBody NewExamDto examDto, Authentication authentication) {
        final var examId = examService.endExam(String.valueOf(authentication.getPrincipal()), examDto.getQuizId(), examApiMapper.map(examDto.getAnswers()));
        return examApiMapper.map(examService.getExamDetails(examId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ExamResponseDto> exams(Authentication authentication) {
        return examApiMapper.mapToDto(examService.getUserExams(String.valueOf(authentication.getPrincipal())));
    }

}
