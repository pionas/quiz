package info.pionas.quiz.web.home;

import info.pionas.quiz.api.AbstractIT;
import org.junit.jupiter.api.Test;

class HomeControllerIT extends AbstractIT {

    @Test
    void should_return_ok() {
        //given
        //when
        final var response = webTestClient
                .get().uri("/")
                .exchange();
        //then
        response.expectStatus().isOk();
    }

}