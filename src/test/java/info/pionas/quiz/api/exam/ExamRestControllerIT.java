package info.pionas.quiz.api.exam;

import com.fasterxml.jackson.databind.JsonNode;
import info.pionas.quiz.api.AbstractIT;
import info.pionas.quiz.api.exam.api.ExamAnswerResponseDto;
import info.pionas.quiz.api.exam.api.ExamResponseDto;
import info.pionas.quiz.api.exam.api.NewExamAnswerDto;
import info.pionas.quiz.api.exam.api.NewExamDto;
import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class ExamRestControllerIT extends AbstractIT {

    @Test
    void should_throw_unauthorized_when_try_to_end_exam_by_quest() {
        //given
        //when
        final var response = webTestClient.post().uri("/api/v1/exam")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getResponseBodyContent()).isEmpty();
    }

    @Test
    void should_throw_bad_request_when_try_to_end_exam() throws IOException {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var endExam = new NewExamDto();
        //when
        final var response = webTestClient.post().uri("/api/v1/exam")
                .body(BodyInserters.fromValue(endExam))
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
        assertThat(errors)
                .hasSize(2)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("answers: must not be empty", "quizId: must not be null");
    }

    @Test
    @Sql({"/db/quiz.sql", "/db/question.sql", "/db/answer.sql"})
    void should_end_exam() {
        //given
        final var user = new User("admin", "admin", Collections.emptyList());
        final var endExam = getNewExamDto();
        //when
        final var response = webTestClient
                .post().uri("/api/v1/exam")
                .body(BodyInserters.fromValue(endExam))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(ExamResponseDto.class);
        //then
        ExamResponseDto examResponseDto = response.getResponseBody().blockLast();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(examResponseDto).isNotNull();
        assertThat(examResponseDto.getCorrectAnswer()).isEqualTo(0);
        assertThat(examResponseDto.getAnswers())
                .hasSize(1)
                .containsExactlyInAnyOrder(getExamAnswerResponseDto());
    }

    @Test
    @Sql({"/db/quiz.sql", "/db/question.sql", "/db/answer.sql"})
    void should_throw_bad_request_when_answer_for_question_not_exist() throws IOException {
        //given
        final var endExam = new NewExamDto();
        endExam.setQuizId(UUID.fromString("b4a63897-60f7-4e94-846e-e199d8734144"));
        endExam.setAnswers(List.of(getAnswer("aaf75e14-3544-4d14-8acf-035037f7f4b4")));

        final var user = new User("admin", "admin", Collections.emptyList());
        //when
        final var response = webTestClient.post().uri("/api/v1/exam")
                .body(BodyInserters.fromValue(endExam))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getResponseBodyContent()).isNotNull();
        final var errorJson = objectMapper.readTree(response.getResponseBodyContent());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        assertThat(errors)
                .hasSize(1)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("There is no answer to the question ce703f5b-274c-4398-b855-d461887c7ed5");
    }

    @Test
    @Sql({"/db/quiz.sql", "/db/question.sql", "/db/answer.sql", "/db/exam_result.sql"})
    void should_return_user_exam_list() {
        //given
        final var user = new User("admin", "admin", Collections.emptyList());
        //when
        final var response = webTestClient
                .get().uri("/api/v1/exam")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange();
        //then
        response.expectStatus().isOk()
                .expectHeader().contentType("text/event-stream;charset=UTF-8")
                .expectBodyList(ExamResponseDto.class)
                .consumeWith(listEntityExchangeResult -> {
                    List<ExamResponseDto> examResponseDtos = listEntityExchangeResult.getResponseBody();
                    assertThat(examResponseDtos).hasSize(2);
                    ExamResponseDto examResponseDto1 = examResponseDtos.getFirst();
                    assertThat(examResponseDto1).isNotNull();
                    assertThat(examResponseDto1.getId()).isEqualTo(UUID.fromString("a375ca97-33f9-4251-aaa0-f9d95b55003f"));
                    ExamResponseDto examResponseDto2 = examResponseDtos.getLast();
                    assertThat(examResponseDto2).isNotNull();
                    assertThat(examResponseDto2.getId()).isEqualTo(UUID.fromString("b8acee24-edaf-4725-876c-c37ec4512a8c"));
                });
    }

    private NewExamDto getNewExamDto() {
        final var newExamDto = new NewExamDto();
        newExamDto.setQuizId(UUID.fromString("b4a63897-60f7-4e94-846e-e199d8734144"));
        newExamDto.setAnswers(List.of(getAnswer("ce703f5b-274c-4398-b855-d461887c7ed5")));
        return newExamDto;
    }

    private NewExamAnswerDto getAnswer(String questionId) {
        final var newExamAnswerDto = new NewExamAnswerDto();
        newExamAnswerDto.setAnswerId(UUID.fromString("a4bd2133-d4e8-4b73-9264-4afb02096ffd"));
        newExamAnswerDto.setQuestionId(UUID.fromString(questionId));
        return newExamAnswerDto;
    }

    private ExamAnswerResponseDto getExamAnswerResponseDto() {
        final var examAnswerResponseDto = new ExamAnswerResponseDto();
        examAnswerResponseDto.setAnswerId(UUID.fromString("a4bd2133-d4e8-4b73-9264-4afb02096ffd"));
        examAnswerResponseDto.setQuestionId(UUID.fromString("ce703f5b-274c-4398-b855-d461887c7ed5"));
        examAnswerResponseDto.setCorrect(false);
        return examAnswerResponseDto;
    }
}