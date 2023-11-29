package info.pionas.quiz.api.quiz;

import info.pionas.quiz.api.AbstractIT;
import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class QuizRestControllerIT extends AbstractIT {

    @Test
    void should_throw_unauthorized_when_try_to_create_quiz_by_quest() throws IOException {
        //given
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getResponseBodyContent()).isEmpty();
    }

    @Test
    void should_create_quiz() {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var newQuizDto = getNewQuizDto();
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz")
                .body(BodyInserters.fromValue(newQuizDto))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(QuizDto.class);
        //then
        QuizDto quizDto = response.getResponseBody().blockLast();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(quizDto).isNotNull();
        assertThat(quizDto.getTitle()).isEqualTo(newQuizDto.getTitle());
        assertThat(quizDto.getDescription()).isEqualTo(newQuizDto.getDescription());
        List<QuestionDto> questions = quizDto.getQuestions();
        assertThat(questions).hasSize(2);
        final var quizEntity = dbUtil.em().find(QuizEntity.class, quizDto.getId());
        assertThat(quizEntity).isNotNull();
        assertThat(quizEntity.getTitle()).isEqualTo(newQuizDto.getTitle());
        assertThat(quizEntity.getDescription()).isEqualTo(newQuizDto.getDescription());
    }

    @Test
    void should_throw_bad_request_when_try_to_create_quiz() throws IOException {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var newQuizDto = NewQuizDto.builder().build();
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz")
                .body(BodyInserters.fromValue(newQuizDto))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getResponseBodyContent()).isNotNull();
        final var errorJson = objectMapper.readTree(response.getResponseBodyContent());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        assertThat(errors.get(0).asText()).isEqualTo("title: must not be blank");
    }

    private NewQuizDto getNewQuizDto() {
        return NewQuizDto.builder()
                .title("Title")
                .description("Description")
                .questions(List.of(createQuestion("Spring is the best JAVA framework"), createQuestion("Do you like me?")))
                .build();
    }

    private NewQuestionDto createQuestion(String content) {
        return NewQuestionDto.builder()
                .content(content)
                .answers(List.of(createAnswer("yes", true), createAnswer("no", false)))
                .build();
    }

    private NewAnswerDto createAnswer(String content, boolean correct) {
        return NewAnswerDto.builder()
                .content(content)
                .correct(correct)
                .build();
    }
}