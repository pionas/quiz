package info.pionas.quiz.api.quiz;

import info.pionas.quiz.api.quiz.api.QuizResponseDto;
import info.pionas.quiz.domain.quiz.api.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
class QuizRestController {

    private final QuizService quizService;
    private final QuizApiMapper quizApiMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    QuizResponseDto create(@Valid @RequestBody NewQuizDto quizDto, Authentication authentication) {
        return quizApiMapper.map(quizService.createQuiz(quizApiMapper.map(String.valueOf(authentication.getPrincipal()), quizDto)));
    }

    @PostMapping("{quizId}/question")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    QuizResponseDto addQuestion(@PathVariable UUID quizId, @Valid @RequestBody NewQuestionDto questionDto) {
        return quizApiMapper.map(quizService.addQuestionToQuiz(quizId, quizApiMapper.map(questionDto)));
    }

    @DeleteMapping("{quizId}/question/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    QuizResponseDto removeQuestion(@PathVariable UUID quizId, @PathVariable UUID questionId) {
        return quizApiMapper.map(quizService.removeQuestionFromQuiz(quizId, questionId));
    }

    @PatchMapping("{quizId}/question")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    QuizResponseDto updateQuestion(@PathVariable UUID quizId, @Valid @RequestBody UpdateQuestionDto updateQuestionDto) {
        return quizApiMapper.map(quizService.updateQuestionFromQuiz(quizId, quizApiMapper.map(updateQuestionDto)));
    }

}
