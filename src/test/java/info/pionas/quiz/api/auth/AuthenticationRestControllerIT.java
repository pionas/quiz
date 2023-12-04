package info.pionas.quiz.api.auth;

import com.fasterxml.jackson.databind.JsonNode;
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
        assertThat(errors)
                .hasSize(2)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("username: must not be blank", "password: must not be blank");
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
        assertThat(response.getResponseBodyContent()).isNotNull();
        final var errorJson = objectMapper.readTree(response.getResponseBodyContent());
        final var errors = StreamSupport
                .stream(errorJson.get("errors").spliterator(), false)
                .toList();
        assertThat(errors)
                .hasSize(1)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("User user not exist");
    }

    @Test
    void should_register() {
        //given
        final var newUserRequest = new NewUserRequest("newuser", "password", "password");
        //when
        final var response = webTestClient.post().uri("/api/v1/register")
                .body(BodyInserters.fromValue(newUserRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(AuthResponse.class);
        //then
        AuthResponse authResponse = response.getResponseBody().blockLast();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.getToken()).isNotBlank();
    }

    @Test
    void should_throw_bad_request_when_confirm_password_is_invalid() throws IOException {
        //given
        final var newUserRequest = new NewUserRequest("userwithinvalidconfirmpassword", "password", "invalidPassword");
        //when
        final var response = webTestClient.post().uri("/api/v1/register")
                .body(BodyInserters.fromValue(newUserRequest))
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
        assertThat(errors)
                .hasSize(1)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("Password doesn't matched");
    }

    @Test
    void should_throw_bad_request_when_try_to_register() throws IOException {
        //given
        final var newUserRequest = new NewUserRequest();
        //when
        final var response = webTestClient.post().uri("/api/v1/register")
                .body(BodyInserters.fromValue(newUserRequest))
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
        assertThat(errors)
                .hasSize(3)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("username: must not be blank", "password: must not be blank", "confirmPassword: must not be blank");
    }

    @Test
    void should_throw_bad_request_when_try_to_register_but_username_already_exist() throws IOException {
        //given
        final var newUserRequest = new NewUserRequest("user", "password", "password");
        //when
        final var response = webTestClient.post().uri("/api/v1/register")
                .body(BodyInserters.fromValue(newUserRequest))
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
        assertThat(errors)
                .hasSize(1)
                .extracting(JsonNode::asText)
                .containsExactlyInAnyOrder("Username user already in use");
    }

}