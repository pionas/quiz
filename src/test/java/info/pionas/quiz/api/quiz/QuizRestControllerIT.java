package info.pionas.quiz.api.quiz;

import info.pionas.quiz.api.AbstractIT;
import info.pionas.quiz.infrastructure.database.quiz.QuizEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

class QuizRestControllerIT extends AbstractIT {

    @Test
    void should_create_quiz() {
        //given
        final var newQuizDto = getNewQuizDto();
        //when
        final var response = apiRestClient.postForEntity("/quiz", newQuizDto, QuizDto.class);
        //then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        QuizDto quizDto = response.getBody();
        Assertions.assertThat(quizDto).isNotNull();
        Assertions.assertThat(quizDto.getTitle()).isEqualTo(newQuizDto.getTitle());
        Assertions.assertThat(quizDto.getDescription()).isEqualTo(newQuizDto.getDescription());
        List<QuestionDto> questions = quizDto.getQuestions();
        Assertions.assertThat(questions).hasSize(2);
        final var quizEntity = dbUtil.em().find(QuizEntity.class, quizDto.getId());
        Assertions.assertThat(quizEntity).isNotNull();
        Assertions.assertThat(quizEntity.getTitle()).isEqualTo(newQuizDto.getTitle());
        Assertions.assertThat(quizEntity.getDescription()).isEqualTo(newQuizDto.getDescription());

    }

    @Test
    void should_throw_bad_request_when_try_to_create_quiz() throws IOException {
        //given
        final var newQuizDto = NewQuizDto.builder().build();
        //when
        final var exception = Assertions.catchThrowableOfType(() -> apiRestClient.postForEntity("/quiz", newQuizDto, QuizDto.class), HttpClientErrorException.class);
        //then
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(exception.getResponseBodyAsByteArray()).isNotNull();
        final var errorJson = objectMapper.readTree(exception.getResponseBodyAsByteArray());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        Assertions.assertThat(errors.get(0).asText()).isEqualTo("title: must not be blank");
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