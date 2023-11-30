 package info.pionas.quiz.api.quiz;

import info.pionas.quiz.api.AbstractIT;
import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class QuizRestControllerIT extends AbstractIT {

    @Test
    void should_throw_unauthorized_when_try_to_create_quiz_by_quest() {
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
        final var user = new User("admin", "admin", List.of(Role.ROLE_ADMIN));
        final var newQuizDto = getNewQuizDto();
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz")
                .body(BodyInserters.fromValue(newQuizDto))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(QuizResponseDto.class);
        //then
        QuizResponseDto quizResponseDto = response.getResponseBody().blockLast();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(quizResponseDto).isNotNull();
        assertThat(quizResponseDto.getTitle()).isEqualTo(newQuizDto.getTitle());
        assertThat(quizResponseDto.getDescription()).isEqualTo(newQuizDto.getDescription());
        List<QuestionDto> questions = quizResponseDto.getQuestions();
        assertThat(questions).hasSize(2);
        final var quizEntity = dbUtil.em().find(QuizEntity.class, quizResponseDto.getId());
        assertThat(quizEntity).isNotNull();
        assertThat(quizEntity.getTitle()).isEqualTo(newQuizDto.getTitle());
        assertThat(quizEntity.getDescription()).isEqualTo(newQuizDto.getDescription());
    }

    @Test
    void should_throw_forbidden_when_try_to_create_quiz() throws IOException {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var newQuizDto = getNewQuizDto();
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz")
                .body(BodyInserters.fromValue(newQuizDto))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getResponseBodyContent()).isNotNull();
        final var errorJson = objectMapper.readTree(response.getResponseBodyContent());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        assertThat(errors.get(0).asText()).isEqualTo("Access Denied");
    }

    @Test
    void should_throw_bad_request_when_try_to_create_quiz() throws IOException {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var newQuizDto = new NewQuizDto();
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

    @Test
    void should_throw_unauthorized_when_try_to_add_question_to_quiz_by_quest() {
        //given
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz/f57342a1-8413-4d45-8465-6b41b8d72d3e/answer")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getResponseBodyContent()).isEmpty();
    }

    @Test
    @Sql("/db/quiz.sql")
    void should_add_question_to_quiz() {
        //given
        final var user = new User("admin", "admin", List.of(Role.ROLE_ADMIN));
        final var newQuestionDto = createQuestion("Spring is the best JAVA framework");
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz/b4a63897-60f7-4e94-846e-e199d8734144/answer")
                .body(BodyInserters.fromValue(newQuestionDto))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(QuizResponseDto.class);
        //then
        QuizResponseDto quizResponseDto = response.getResponseBody().blockLast();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(quizResponseDto).isNotNull();
        assertThat(quizResponseDto.getTitle()).isEqualTo("First question");
        assertThat(quizResponseDto.getDescription()).isEqualTo("This is first question without answers");
        final var questions = quizResponseDto.getQuestions();
        assertThat(questions).hasSize(1);
        final var questionDto = questions.get(0);
        assertThat(questionDto).isNotNull();
        assertThat(questionDto.getContent()).isEqualTo(newQuestionDto.getContent());
        final var answers = questionDto.getAnswers();
        assertThat(answers).hasSize(2);
        AnswerDto answerDto1 = answers.get(0);
        assertThat(answerDto1).isNotNull();
        assertThat(answerDto1.getContent()).isEqualTo(newQuestionDto.getAnswers().get(0).getContent());
        assertThat(answerDto1.getCorrect()).isEqualTo(newQuestionDto.getAnswers().get(0).getCorrect());
        AnswerDto answerDto2 = answers.get(1);
        assertThat(answerDto2).isNotNull();
        assertThat(answerDto2.getContent()).isEqualTo(newQuestionDto.getAnswers().get(1).getContent());
        assertThat(answerDto2.getCorrect()).isEqualTo(newQuestionDto.getAnswers().get(1).getCorrect());
        final var quizEntity = dbUtil.em().find(QuizEntity.class, quizResponseDto.getId());
        assertThat(quizEntity).isNotNull();
        assertThat(quizEntity.getTitle()).isEqualTo("First question");
        assertThat(quizEntity.getDescription()).isEqualTo("This is first question without answers");
    }

    @Test
    void should_throw_forbidden_when_try_to_add_question_to_quiz() throws IOException {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var newQuestionDto = createQuestion("Spring is the best JAVA framework");
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz/b4a63897-60f7-4e94-846e-e199d8734144/answer")
                .body(BodyInserters.fromValue(newQuestionDto))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(HttpClientErrorException.class);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getResponseBodyContent()).isNotNull();
        final var errorJson = objectMapper.readTree(response.getResponseBodyContent());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        assertThat(errors.get(0).asText()).isEqualTo("Access Denied");
    }

    @Test
    void should_throw_bad_request_when_try_to_add_answer_to_quiz() throws IOException {
        //given
        final var user = new User("user", "user", List.of(Role.ROLE_USER));
        final var newQuestionDto = new NewQuestionDto();
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz/b4a63897-60f7-4e94-846e-e199d8734144/answer")
                .body(BodyInserters.fromValue(newQuestionDto))
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
        assertThat(errors.get(0).asText()).isEqualTo("content: must not be blank");
    }

    @Test
    void should_throw_not_found_when_quiz_by_id_not_exist() throws IOException {
        //given
        final var user = new User("admin", "admin", List.of(Role.ROLE_ADMIN));
        final var newQuestionDto = createQuestion("Spring is the best JAVA framework");
        //when
        final var response = webTestClient.post().uri("/api/v1/quiz/b4a63897-60f7-4e94-846e-e199d8734144/answer")
                .body(BodyInserters.fromValue(newQuestionDto))
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
        assertThat(errors.get(0).asText()).isEqualTo("Quiz by ID b4a63897-60f7-4e94-846e-e199d8734144 not exist");
    }

    private NewQuizDto getNewQuizDto() {
        final var newQuizDto = new NewQuizDto();
        newQuizDto.setTitle("Title");
        newQuizDto.setDescription("Description");
        newQuizDto.setQuestions(List.of(createQuestion("Spring is the best JAVA framework"), createQuestion("Do you like me?")));
        return newQuizDto;
    }

    private NewQuestionDto createQuestion(String content) {
        final var newQuestionDto = new NewQuestionDto();
        newQuestionDto.setContent(content);
        newQuestionDto.setAnswers(List.of(createAnswer("yes", true), createAnswer("no", false)));
        return newQuestionDto;
    }

    private NewAnswerDto createAnswer(String content, boolean correct) {
        final var newAnswerDto = new NewAnswerDto();
        newAnswerDto.setContent(content);
        newAnswerDto.setCorrect(correct);
        return newAnswerDto;
    }
}