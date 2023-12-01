package info.pionas.quiz.api;

import info.pionas.quiz.domain.user.api.Role;
import info.pionas.quiz.domain.user.api.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorHandlerRestControllerIT extends AbstractIT {

    @Test
    void should_throw_bad_request() {
        final var user = new User("admin", "admin", List.of(Role.ROLE_ADMIN));
        final var response = webTestClient.post().uri("/api/v1/quiz/b4a63897-60f7-4e94-846e-e199d8734144/question")
                .body(BodyInserters.fromValue(user))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(HttpClientErrorException.class);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_throw_internal_server_error() {
        final var user = new User("admin", "admin", List.of(Role.ROLE_ADMIN));
        final var response = webTestClient.post().uri("/api/v1/quiz/b4a63897-60f7-4e94-846e-e199d8734144/question")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken(user))
                .exchange()
                .returnResult(HttpClientErrorException.class);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}