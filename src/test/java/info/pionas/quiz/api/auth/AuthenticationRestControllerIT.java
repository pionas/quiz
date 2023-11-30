package info.pionas.quiz.api.auth;

import info.pionas.quiz.api.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationRestControllerIT extends AbstractIT {

    @Test
    void should_login() {
        //given
        final var authRequest = new AuthRequest("user", "user");
        //when
        final var response = webTestClient.post().uri("/api/v1/login")
                .body(BodyInserters.fromValue(authRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(AuthResponse.class);
        //then
        AuthResponse authResponse = response.getResponseBody().blockLast();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.getToken()).isNotBlank();
    }

    @Test
    void should_throw_bad_request_when_try_to_login() throws IOException {
        //given
        final var authRequest = new AuthRequest();
        //when
        final var response = webTestClient.post().uri("/api/v1/login")
                .body(BodyInserters.fromValue(authRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getResponseBodyContent()).isNotNull();
        final var errorJson = objectMapper.readTree(response.getResponseBodyContent());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        assertThat(errors.get(0).asText()).isEqualTo("username: must not be blank");
        assertThat(errors.get(1).asText()).isEqualTo("password: must not be blank");
    }

    @Test
    void should_throw_invalid_data_when_try_to_login() throws IOException {
        //given
        final var authRequest = new AuthRequest("user", "password");
        //when
        final var response = webTestClient.post().uri("/api/v1/login")
                .body(BodyInserters.fromValue(authRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(HttpClientErrorException.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getResponseBodyContent()).isEmpty();
    }

}