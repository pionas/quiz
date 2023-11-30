package info.pionas.quiz.api.quiz;

import info.pionas.quiz.domain.quiz.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    Mono<QuizResponseDto> create(@Valid @RequestBody NewQuizDto quizDto) {
        return Mono.just(quizApiMapper.map(quizService.createQuiz(quizApiMapper.map(quizDto))));
    }

    @PostMapping("{quizId}/answer")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    Mono<QuizResponseDto> addQuestion(@PathVariable UUID quizId, @Valid @RequestBody NewQuestionDto questionDto) {
        return Mono.just(quizApiMapper.map(quizService.addQuestionToQuiz(quizId, quizApiMapper.map(questionDto))));
    }

}
