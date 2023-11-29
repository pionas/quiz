package info.pionas.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.pionas.quiz.api.security.JWTUtil;
import info.pionas.quiz.domain.user.api.User;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {
        "spring.profiles.active=it",
        "server.port=50000",
        "spring.web.locale=en_US",
        "server.shutdown=graceful"
})
@Import({DbITConfiguration.class})
public class AbstractIT {


    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected TestDbUtil dbUtil;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @AfterEach
    void tearDown() {
        dbUtil.clean();
    }

    protected String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }
}
